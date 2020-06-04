package com.noenv.wiremongo.mapping;

import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import org.bson.types.ObjectId;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

public abstract class WithDocument<C extends WithDocument<C>> extends WithCollection<String, C> {

  public abstract static class WithDocumentCommand extends WithCollectionCommand {

    private final JsonObject document;

    public WithDocumentCommand(String method, String collection, JsonObject document) {
      super(method, collection);
      this.document = document;
    }

    @Override
    public String toString() {
      return super.toString() + ", document: " + document;
    }
  }

  private Matcher<JsonObject> document;

  public WithDocument(String method) {
    super(method);
  }

  public WithDocument(JsonObject json) {
    super(json);
    document = Matcher.create(json.getJsonObject("document"));
  }

  public C withDocument(JsonObject document) {
    return withDocument(equalTo(document));
  }

  public C withDocument(Matcher<JsonObject> document) {
    this.document = document;
    return self();
  }

  public C returnsObjectId() {
    return returns(ObjectId.get().toHexString());
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof WithDocumentCommand)) {
      return false;
    }
    WithDocumentCommand c = (WithDocumentCommand) cmd;
    return document == null || document.matches(c.document);
  }

  @Override
  protected String parseResponse(Object jsonValue) {
    return (String) jsonValue;
  }
}
