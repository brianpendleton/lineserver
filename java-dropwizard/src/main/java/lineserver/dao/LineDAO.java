package lineserver.dao;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Optional;

public class LineDAO {

    private MongoCollection collection;

    public LineDAO(String server, String database, String collection) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("lineserver");
        this.collection = db.getCollection("lines");
    }

    public Optional<Document> findByLineNumber(long lineNumber) {
        FindIterable<Document> docIter = collection.find(Filters.eq("lineNumber", lineNumber));
        return Optional.of(docIter.first());
    }
}
