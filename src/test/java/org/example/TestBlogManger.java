package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBlogManger {
    private BlogManager blogManager;
    private static DBConnection dbConnection;

    @AfterAll
    public static void tearDownAfterClass() {
        if (dbConnection != null) {
            dbConnection.close();
        }
    }
    @BeforeEach
    public void setUp() {
        dbConnection = new DBConnection();
        blogManager = new BlogManager();
    }
    @Test
    public void TestAddPost(){
        dbConnection = new DBConnection();
        blogManager = new BlogManager();
        String title = "Test Title";
        String postContent = "Test Content";
        List<String> tags = Arrays.asList("test", "blog");
        String category = "Testing";

        blogManager.addPost(title, postContent, tags, category);

        MongoCollection<Document> collection = dbConnection.getCollection();
        Document query = new Document("title",title);
        Document post = collection.find(query).first();

        assertEquals(title, post.getString("title"));
        assertEquals(postContent, post.getString("content"));
        assertEquals(tags, post.getList("tags", String.class));
        assertEquals(category, post.getString("category"));
        assertEquals((Integer)0,post.getInteger("likes"));
        assertTrue(Instant.parse(post.getString("timestamp")).isBefore(Instant.now()));
    }
    @Test
    public void TestAddPostWithEmptyTags(){
        dbConnection = new DBConnection();
        blogManager = new BlogManager();
        String title = "Empty Tags Test";
        String postContent = "Testing with empty tags";
        List<String> tags = new ArrayList<>();
        String category = "Testing";

        blogManager.addPost(title, postContent, tags, category);

        MongoCollection<Document> collection = dbConnection.getCollection();
        Document query = new Document("title",title);
        Document post = collection.find(query).first();

        assertEquals(title, post.getString("title"));
        assertEquals(postContent, post.getString("content"));
        assertEquals(tags, post.getList("tags", String.class));
        assertEquals(category, post.getString("category"));
        assertEquals((Integer)0,post.getInteger("likes"));
        assertTrue(Instant.parse(post.getString("timestamp")).isBefore(Instant.now()));
    }
    @Test
    public void TestAddPostWithLongTitleAndContent(){
        dbConnection = new DBConnection();
        blogManager = new BlogManager();
        String title = "Very Long Title ".repeat(1000); // 12000 characters
        String postContent = "Very Long Content ".repeat(1000); // 14000 characters
        List<String> tags = Arrays.asList("long", "content");
        String category = "Testing";

        blogManager.addPost(title, postContent, tags, category);

        MongoCollection<Document> collection = dbConnection.getCollection();
        Document query = new Document("title",title);
        Document post = collection.find(query).first();

        assertEquals(title, post.getString("title"));
        assertEquals(postContent, post.getString("content"));
        assertEquals(tags, post.getList("tags", String.class));
        assertEquals(category, post.getString("category"));
        assertEquals((Integer)0,post.getInteger("likes"));
        assertTrue(Instant.parse(post.getString("timestamp")).isBefore(Instant.now()));
    }
    @Test
    public void testAddPostWithSpecialCharactersInCategory(){
        dbConnection = new DBConnection();
        blogManager = new BlogManager();
        String title = "Special Characters Test";
        String postContent = "Testing with special characters in category";
        List<String> tags = Arrays.asList("special", "characters");
        String category = "!@#$%^&*()";

        blogManager.addPost(title, postContent, tags, category);

        MongoCollection<Document> collection = dbConnection.getCollection();
        Document query = new Document("title",title);
        Document post = collection.find(query).first();

        assertEquals(title, post.getString("title"));
        assertEquals(postContent, post.getString("content"));
        assertEquals(tags, post.getList("tags", String.class));
        assertEquals(category, post.getString("category"));
        assertEquals((Integer)0,post.getInteger("likes"));
        assertTrue(Instant.parse(post.getString("timestamp")).isBefore(Instant.now()));
    }
    @Test
    public void testAddPostWithLargeNumberOfTags(){
        dbConnection = new DBConnection();
        blogManager = new BlogManager();
        String title = "Large Number of Tags Test";
        String postContent = "Testing with a large number of tags";
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            tags.add("tag" + i);
        }
        String category = "Testing";

        blogManager.addPost(title, postContent, tags, category);

        MongoCollection<Document> collection = dbConnection.getCollection();
        Document query = new Document("title",title);
        Document post = collection.find(query).first();

        assertEquals(title, post.getString("title"));
        assertEquals(postContent, post.getString("content"));
        assertEquals(tags, post.getList("tags", String.class));
        assertEquals(category, post.getString("category"));
        assertEquals((Integer)0,post.getInteger("likes"));
        assertTrue(Instant.parse(post.getString("timestamp")).isBefore(Instant.now()));
    }
}
