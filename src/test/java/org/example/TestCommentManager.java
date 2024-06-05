package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestCommentManager {
    @Test
    public void test_add_comment_with_valid_data() {
        DBConnection dbConnection = new DBConnection();
        CommentManager commentManager = new CommentManager("CommentTest");
        BlogManager blogManager = new BlogManager("BlogCommentTest");
        blogManager.addPost("Title1", "Content1", Arrays.asList("tag1"), "Category1");
        blogManager.addPost("Title2", "Content2", Arrays.asList("tag2"), "Category2");

        Blog post = blogManager.viewPost("Title1");
        String postId = post.getId();
        String content = "This is a test comment.";
        String author = "TestAuthor";

        commentManager.addComment(postId, content, author);

        MongoCollection<Document> collection = dbConnection.getCollection("CommentTest");
        Document query = new Document("postId", postId).append("content", content).append("author", author);
        Document comment = collection.find(query).first();

        assertEquals(postId, comment.getString("postId"));
        assertEquals(content, comment.getString("content"));
        assertEquals(author, comment.getString("author"));
        assertTrue(Instant.parse(comment.getString("timestamp")).isBefore(Instant.now()));
        dbConnection.getCollection("CommentTest").deleteMany(new Document());
        dbConnection.getCollection("BlogCommentTest").deleteMany(new Document());
    }

    @Test
    public void test_retrieves_comments_for_valid_postId() {
        DBConnection dbConnection = new DBConnection();
        CommentManager commentManager = new CommentManager("CommentTest");
        BlogManager blogManager = new BlogManager("BlogCommentTest");
        blogManager.addPost("Title1", "Content1", Arrays.asList("tag1"), "Category1");

        Blog post = blogManager.viewPost("Title1");
        String postId = post.getId();
        String content = "This is a test comment.";
        String author = "TestAuthor";

        commentManager.addComment(postId, content, author);
        String author2 = "TestAuthor 2";
        String content2 = "This is a test comment 2.";
        commentManager.addComment(postId, content2, author2);

        ArrayList<Comment> comments = commentManager.getCommentsForPost(postId);
        for (Comment comment: comments){
            System.out.println(comment);
        }
        assertEquals(2, comments.size());
        assertEquals(content, comments.get(0).getContent());
        assertEquals(author, comments.get(0).getAuthor());
        assertTrue(comments.get(0).getTimestamp().isBefore(Instant.now()));
        assertEquals(content2, comments.get(1).getContent());
        assertEquals(author2, comments.get(1).getAuthor());
        assertTrue(comments.get(1).getTimestamp().isBefore(Instant.now()));


        dbConnection.getCollection("CommentTest").deleteMany(new Document());
        dbConnection.getCollection("BlogCommentTest").deleteMany(new Document());

    }

}
