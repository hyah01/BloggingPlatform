package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import javax.print.Doc;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;


public class BlogManager {
    MongoCollection<Document> collection;
    public BlogManager(MongoCollection<Document> collection){
        this.collection = collection;
    }

    public void addPost(String title, String content, List<String> tags, String category){
        Document post = new Document("title",title)
                .append("content", content)
                .append("tags", tags)
                .append("category", category)
                .append("likes", 0)
                .append("timestamp", Instant.now().toString());
        collection.insertOne(post);
    }

    public ArrayList<Document> getPosts(){
        ArrayList<Document> posts = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()){
            while (cursor.hasNext()){
                posts.add(cursor.next());
            }
        }
        return posts;
    }

    public void editPost(String postTitle, String newTitle, String newContent, List<String> newTags, String newCategory){
        collection.updateOne(Filters.eq("title",postTitle), new Document("$set", new Document("title",newTitle)
                .append("content", newContent)
                .append("tags", newTags)
                .append("category", newCategory)));
    }
}
