package org.example;

import java.time.Instant;
import java.util.List;

public class Blog {
    private String id;
    private String title;
    private String content;
    private List<String> tags;
    private String category;
    private Instant timestamp;

    public Blog(String id, String title, String content, List<String> tags, String category, Instant timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.category = category;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", category='" + category + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
