package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBlogManger {
    private BlogManager blogManager;

    @Test
    public void TestAddPost(){
        DBConnection dbConnection = new DBConnection("BlogTest");
        blogManager = new BlogManager(dbConnection.getCollection());
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
        DBConnection dbConnection = new DBConnection("BlogTest");
        blogManager = new BlogManager(dbConnection.getCollection());
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
        DBConnection dbConnection = new DBConnection("BlogTest");
        blogManager = new BlogManager(dbConnection.getCollection());
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
        DBConnection dbConnection = new DBConnection("BlogTest");
        blogManager = new BlogManager(dbConnection.getCollection());
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
        DBConnection dbConnection = new DBConnection("BlogTest");
        blogManager = new BlogManager(dbConnection.getCollection());
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

    @Test
    public void testReturnsAllPosts() {
        DBConnection dbConnection = new DBConnection("BlogTestGet");
        blogManager = new BlogManager(dbConnection.getCollection());

        // Add multiple posts
        blogManager.addPost("Title1", "Content1", Arrays.asList("tag1"), "Category1");
        blogManager.addPost("Title2", "Content2", Arrays.asList("tag2"), "Category2");

        ArrayList<Document> posts = blogManager.getPosts();
        assertEquals(2, posts.size());
        // Delete all documents from the collection
        dbConnection.getCollection().deleteMany(new Document());
    }
}
