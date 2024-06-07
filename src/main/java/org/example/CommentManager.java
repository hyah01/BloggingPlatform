package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// manages the comments
public class CommentManager {
    private MongoCollection<Document> collection;

    // get the right collection
    public CommentManager(String collection){
        this.collection = new DBConnection().getCollection(collection);
    }

    // add comments
    public void addComment(String postId, String content, String author) {
        Document commentDoc = new Document("postId", postId)
                .append("content", content)
                .append("author", author)
                .append("timestamp", Instant.now().toString());
        collection.insertOne(commentDoc);
    }

    // get the comments for the post with the correct id
    public ArrayList<Comment> getCommentsForPost(String postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        collection.find(new Document("postId", postId)).iterator().forEachRemaining(doc -> {
            String content = doc.getString("content");
            String author = doc.getString("author");
            Instant timestamp = Instant.parse(doc.getString("timestamp"));
            comments.add(new Comment(content, author, timestamp));
        });
        return comments;
    }

    // delete a comment when the post get deleted
    public void deleteComments(String postId){
        collection.deleteMany(new Document("postID",postId));
    }
}
