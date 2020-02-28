
## About

Wiremongo is an alternative implementation of the Vert.x `MongoClient` interface that can be used to mock mongo calls. It is most useful for unit testing mongo database code and is more lightweight than [vertx-embedded-mongo-db](https://github.com/vert-x3/vertx-embedded-mongo-db) because it doesn't run an entire mongo instance. 

## Quick Start

Add the the dependency, e.g. Maven:

```xml
<dependency>
  <groupId>com.noenv</groupId>
  <artifactId>vertx-wiremongo</artifactId>
  <version>3.8.6-SNAPSHOT</version>
  <scope>test</scrope>
</dependency>
```

Imagine you have a mongodb to keep track of your fruit. In your application you have a class that uses `io.vertx.ext.mongo.MongoClient` for the database operations:

```java
public class FruitDatabase {

  private final MongoClient mongo;

  public FruitDatabase(MongoClient mongo) {
    this.mongo = mongo;
  }

  public Future<Void> addApple(int mass, Instant expiration) {
    return insertFruit("apple", mass, expiration).mapEmpty();
  }

  public Future<Void> addBanana(int mass, Instant expiration) {
    return insertFruit("banana", mass, expiration).mapEmpty();
  }

  private Future<String> insertFruit(String type, int mass, Instant expiration) {
    var p = Promise.<String>promise();
    mongo.insert("fruits", new JsonObject()
      .put("type", type)
      .put("mass", mass)
      .put("expiration", new JsonObject().put("$date", expiration)), p);
    return p.future();
  }

  public Future<Long> countFruitByType(String type) { }
  public Future<Long> removeExpiredFruit() { }
  // etc.
}
```

To test this class, simply create an instance of `WireMongo`, set up some mocking using its fluent methods, and use it as the `MongoClient` implementation in your tests:

```java
@RunWith(VertxUnitRunner.class)
public class FruitDatabaseTest {

  private WireMongo wiremongo;
  private FruitDatabase db;

  @Before
  public void setUp() {
    wiremongo = new WireMongo();
    db = new FruitDatabase(wiremongo.getClient());
  }

  @Test
  public void testAddApple(TestContext ctx) {
    var expiration = Instant.now().plus(5, ChronoUnit.DAYS);

    wiremongo.insert()
      .inCollection("fruits")
      .withDocument(new JsonObject()
        .put("type", "apple")
        .put("mass", 161)
        .put("expiration", new JsonObject().put("$date", expiration)))
      .returnsObjectId();

    db.addApple(161, expiration)
      .setHandler(ctx.asyncAssertSuccess());
  }
}
```

You can also test error cases:

```java
@Test
public void testInsertError(TestContext ctx) {
  wiremongo.insert()
    .returnsDuplicateKeyError();

  db.addApple(123, Instant.now().plus(3, ChronoUnit.DAYS))
    .setHandler(ctx.asyncAssertFailure());
}
```

_You can find the complete source code of these examples in `src/test/java/com/noenv/wiremongo/examples`._

## Matching

When setting up a mock, all the matching criteria can also be defined by *custom matchers*. The following mock would match all `update` commands that try to update the expiration of bananas:

```java
wiremongo.updateCollection()
  .inCollection("fruits")
  .withQuery(q -> q.getString("type").equals("banana"))
  .withUpdate(u -> u.getJsonObject("$set").containsKey("expiration"))
  .returnsTimeoutException();
```

If you don't specify a custom matcher, `Objects.equals` is used by default. Since a lot of interaction with mongo happens using `JsonObject` and `JsonArray`, there is also a `JsonMatcher` that can be used like this:

```java
import static com.noenv.wiremongo.matching.JsonMatcher.equalToJson;

wiremongo.insert()
  .inCollection("fruits")
  .withDocument(equalToJson(new JsonObject().put("type", "banana"), /*ignoreExtraElements*/ true))
  .returns("2ad7533f");
```

The above example matches all commands that try to insert *bananas* into *fruits*. Note that the json matcher supports a flag `ignoreExtraElements` that allows these insert documents to be matched even if they contain additional fields (e.g. mass & expiration).

## Stubs

Stubs are the *response* part of the mock, i.e. they define how the mock *responds* to commands that match. The most low-level stubs are *custom stubs*:

```java
wiremongo.findOne()
  .inCollection("fruits")
  .stub(() -> new JsonObject()
    .put("type", "apple")
    .put("mass", 123)
    .put("expiration", new JsonObject().put("$date", Instant.now())));
```

Sometimes it may be useful to assert that the application actually invokes the expected mongo command:

```java
@Test
public void testInsert(TestContext ctx) {
  Async async = ctx.async();
  wiremongo.insert()
    .stub(() -> {
      async.countDown();
      return "37bd238fa";
    });

  application.addApple(); // adding an apple should trigger an insert command
}
```

The `returns("1234")` method is just a more convenient way for `stub(() -> "1234")`. 

Stubs can also throw exceptions:

```java
wiremongo.count()
  .stub(() -> { throw new MongoTimeoutException("intentional"); });
```

For the most common errors, wiremongo contains helper methods that match the types and messages of an actual mongo instance (`returnsDuplicateKeyError`, `returnsTimeoutException`, `returnsConnectionException`).

Multiple stubs can be configured for a mock. The stubs are used once each in the order they are added, the last one is used forever. Consider the following mock:

```java
wiremongo.insert()
  .returns("37bd238fa")
  .returns("73ab6cf21")
  .returnsDuplicateKeyError();
```

The above code will return ids for the first two and a duplicate key error for every subsequent insert command.

## Files

Mocks can also be defined in json files. You can ask wiremongo to read files from a directory like this:

```
@Before
public void setUp(TestContext ctx) {
  wiremongo = new WireMongo(vertx);
  wiremongo.readFileMappings("test/resources/wiremongo-files")
    .setHandler(ctx.asyncAssertSuccess());
}
```

The wiremongo json files look like this: 

```json
{
  "method": "insert",
  "collection": {
    "equalTo": "fruits"
  },
  "document": {
    "equalToJson": {
      "type": "banana",
      "mass": 7533
    },
    "ignoreExtraElements": true
  },
  "response": "388adf7ab"
}
```

The details depend on the command that is mocked. To get started, it is easiest to just look at the json file for the command you want to mock in the `src/test/resources/wiremongo-mocks` folder of this project.



