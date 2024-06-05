package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;


public class BlogManager {
    MongoCollection<Document> collection;
    public BlogManager(String collection){
        this.collection = new DBConnection().getCollection(collection);
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

    public ArrayList<Blog> getPosts(){
        ArrayList<Blog> posts = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()){
            while (cursor.hasNext()){
                Document doc = cursor.next();
                ObjectId id = doc.getObjectId("_id");
                String title = doc.getString("title");
                String content = doc.getString("content");
                List<String> tags = doc.getList("tags", String.class);
                String category = doc.getString("category");
                Instant timestamp = Instant.parse(doc.getString("timestamp"));
                posts.add(new Blog(id.toHexString(), title, content, tags, category, timestamp));
            }
        }
        return posts;
    }

    public Blog viewPost(String title){
        Document query = new Document("title",title);
        Document post = collection.find(query).first();
        ObjectId id = post.getObjectId("_id");
        String postTitle = post.getString("title");
        String content = post.getString("content");
        List<String> tags = post.getList("tags", String.class);
        String category = post.getString("category");
        Instant timestamp = Instant.parse(post.getString("timestamp"));
        return (new Blog(id.toHexString(), postTitle, content, tags, category, timestamp));
    }

    public void editPost(String postTitle, String newTitle, String newContent, List<String> newTags, String newCategory){
        collection.updateOne(Filters.eq("title",postTitle), new Document("$set", new Document("title",newTitle)
                .append("content", newContent)
                .append("tags", newTags)
                .append("category", newCategory)));
    }
}
