package org.example;

import org.bson.Document;

import java.time.Instant;
import java.util.List;


public class BlogManager {
    DBConnection dbConnection = new DBConnection();

    public void addPost(String title, String content, List<String> tags, String category){
        Document post = new Document("title",title)
                .append("content", content)
                .append("tags", tags)
                .append("category", category)
                .append("likes", 0)
                .append("timestamp", Instant.now().toString());
        dbConnection.getCollection().insertOne(post);

    }
}
