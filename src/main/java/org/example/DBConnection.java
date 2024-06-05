package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DBConnection {
    private final String CONNECTIONSTRING = "mongodb://localhost:27017";
    private final String DBNAME = "BlogDB";
    private final String DBCOLLECTION = "Blogs";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    // return a connection for other classes to utilize
    public DBConnection() {
        mongoClient = MongoClients.create(CONNECTIONSTRING);
        database = mongoClient.getDatabase(DBNAME);
        collection = database.getCollection(DBCOLLECTION);

    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void close(){
        mongoClient.close();
    }


}
