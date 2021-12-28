package com.noenv.wiremongo;

import com.noenv.wiremongo.mapping.*;
import com.noenv.wiremongo.mapping.aggregate.Aggregate;
import com.noenv.wiremongo.mapping.aggregate.AggregateWithOptions;
import com.noenv.wiremongo.mapping.bulkwrite.BulkWrite;
import com.noenv.wiremongo.mapping.bulkwrite.BulkWriteWithOptions;
import com.noenv.wiremongo.mapping.collection.CreateCollection;
import com.noenv.wiremongo.mapping.collection.CreateCollectionWithOptions;
import com.noenv.wiremongo.mapping.collection.DropCollection;
import com.noenv.wiremongo.mapping.collection.GetCollections;
import com.noenv.wiremongo.mapping.distinct.Distinct;
import com.noenv.wiremongo.mapping.distinct.DistinctBatch;
import com.noenv.wiremongo.mapping.distinct.DistinctBatchWithQuery;
import com.noenv.wiremongo.mapping.distinct.DistinctWithQuery;
import com.noenv.wiremongo.mapping.find.*;
import com.noenv.wiremongo.mapping.index.*;
import com.noenv.wiremongo.mapping.insert.Insert;
import com.noenv.wiremongo.mapping.insert.InsertWithOptions;
import com.noenv.wiremongo.mapping.remove.RemoveDocument;
import com.noenv.wiremongo.mapping.remove.RemoveDocumentWithOptions;
import com.noenv.wiremongo.mapping.remove.RemoveDocuments;
import com.noenv.wiremongo.mapping.remove.RemoveDocumentsWithOptions;
import com.noenv.wiremongo.mapping.replace.ReplaceDocuments;
import com.noenv.wiremongo.mapping.replace.ReplaceDocumentsWithOptions;
import com.noenv.wiremongo.mapping.save.Save;
import com.noenv.wiremongo.mapping.save.SaveWithOptions;
import com.noenv.wiremongo.mapping.update.UpdateCollection;
import com.noenv.wiremongo.mapping.update.UpdateCollectionWithOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface WireMongoCommands {

  default MatchAll matchAll() {
    return addMapping(new MatchAll());
  }

  default Insert insert() {
    return addMapping(new Insert());
  }

  default InsertWithOptions insertWithOptions() {
    return addMapping(new InsertWithOptions());
  }

  default Save save() {
    return addMapping(new Save());
  }

  default SaveWithOptions saveWithOptions() {
    return addMapping(new SaveWithOptions());
  }

  default UpdateCollection<JsonObject> updateCollection() {
    return addMapping(new UpdateCollection<>());
  }

  default UpdateCollection<JsonArray> updateCollectionAggregationPipeline() {
    return addMapping(new UpdateCollection<>("updateCollectionAggregationPipeline"));
  }

  default UpdateCollectionWithOptions<JsonObject> updateCollectionWithOptions() {
    return addMapping(new UpdateCollectionWithOptions<>());
  }

  default UpdateCollectionWithOptions<JsonArray> updateCollectionWithOptionsAggregationPipeline() {
    return addMapping(new UpdateCollectionWithOptions<>("updateCollectionWithOptionsAggregationPipeline"));
  }

  default Find find() {
    return addMapping(new Find());
  }

  default FindWithOptions findWithOptions() {
    return addMapping(new FindWithOptions());
  }

  default FindOne findOne() {
    return addMapping(new FindOne());
  }

  default FindOneAndUpdate findOneAndUpdate() {
    return addMapping(new FindOneAndUpdate());
  }

  default FindOneAndReplace findOneAndReplace() {
    return addMapping(new FindOneAndReplace());
  }

  default FindOneAndReplaceWithOptions findOneAndReplaceWithOptions() {
    return addMapping(new FindOneAndReplaceWithOptions());
  }

  default FindOneAndUpdateWithOptions findOneAndUpdateWithOptions() {
    return addMapping(new FindOneAndUpdateWithOptions());
  }

  default FindOneAndDelete findOneAndDelete() {
    return addMapping(new FindOneAndDelete());
  }

  default FindOneAndDeleteWithOptions findOneAndDeleteWithOptions() {
    return addMapping(new FindOneAndDeleteWithOptions());
  }

  default FindBatch findBatch() {
    return addMapping(new FindBatch());
  }

  default FindBatchWithOptions findBatchWithOptions() {
    return addMapping(new FindBatchWithOptions());
  }

  default CreateCollection createCollection() {
    return addMapping(new CreateCollection());
  }

  default CreateCollectionWithOptions createCollectionWithOptions() {
    return addMapping(new CreateCollectionWithOptions());
  }

  default DropCollection dropCollection() {
    return addMapping(new DropCollection());
  }

  default CreateIndexes createIndexes() {
    return addMapping(new CreateIndexes());
  }

  default CreateIndex createIndex() {
    return addMapping(new CreateIndex());
  }

  default BulkWrite bulkWrite() {
    return addMapping(new BulkWrite());
  }

  default BulkWriteWithOptions bulkWriteWithOptions() {
    return addMapping(new BulkWriteWithOptions());
  }

  default Count count() {
    return addMapping(new Count());
  }

  default CountWithOptions countWithOptions() {
    return addMapping(new CountWithOptions());
  }

  default ListIndexes listIndexes() {
    return addMapping(new ListIndexes());
  }

  default GetCollections getCollections() {
    return addMapping(new GetCollections());
  }

  default CreateIndexWithOptions createIndexWithOptions() {
    return addMapping(new CreateIndexWithOptions());
  }

  default RemoveDocument removeDocument() {
    return addMapping(new RemoveDocument());
  }

  default RemoveDocumentWithOptions removeDocumentWithOptions() {
    return addMapping(new RemoveDocumentWithOptions());
  }

  default RemoveDocuments removeDocuments() {
    return addMapping(new RemoveDocuments());
  }

  default RemoveDocumentsWithOptions removeDocumentsWithOptions() {
    return addMapping(new RemoveDocumentsWithOptions());
  }

  default ReplaceDocuments replaceDocuments() {
    return addMapping(new ReplaceDocuments());
  }

  default ReplaceDocumentsWithOptions replaceDocumentsWithOptions() {
    return addMapping(new ReplaceDocumentsWithOptions());
  }

  default RunCommand runCommand() {
    return addMapping(new RunCommand());
  }

  default Aggregate aggregate() {
    return addMapping(new Aggregate());
  }

  default AggregateWithOptions aggregateWithOptions() {
    return addMapping(new AggregateWithOptions());
  }

  default DropIndex dropIndex() {
    return addMapping(new DropIndex());
  }

  default Distinct distinct() {
    return addMapping(new Distinct());
  }

  default DistinctBatch distinctBatch() {
    return addMapping(new DistinctBatch());
  }

  default DistinctWithQuery distinctWithQuery() {
    return addMapping(new DistinctWithQuery());
  }

  default DistinctBatchWithQuery distinctBatchWithQuery() {
    return addMapping(new DistinctBatchWithQuery());
  }

  <T extends Mapping<?, ?, ?>> T addMapping(T mapping);

  <T extends Mapping<?, ?, ?>> boolean removeMapping(T mapping);
}
