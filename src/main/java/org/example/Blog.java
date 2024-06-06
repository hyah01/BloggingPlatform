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
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ %-49s ║\n", "Blog"));
        sb.append("╠═══════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ %-49s ║\n", "Title: " + title));
        sb.append("╠═══════════════════════════════════════════════════╣\n");
        sb.append("║ Content:                                          ║\n");
        sb.append(wrapText(content, 50));
        sb.append(String.format("║ %-49s ║\n", "Tags: " + tags));
        sb.append(String.format("║ %-49s ║\n", "Category: " + category));
        sb.append(String.format("║ %-49s ║\n", "Timestamp: " + timestamp));
        sb.append("╚═══════════════════════════════════════════════════╝");
        return sb.toString();
    }
    private String wrapText(String text, int maxLength) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            int endIndex = Math.min(index + maxLength, text.length());
            if (endIndex < text.length() && !Character.isWhitespace(text.charAt(endIndex))) {
                // If the character at the end index is not whitespace, find the last whitespace character before it
                while (endIndex > index && !Character.isWhitespace(text.charAt(endIndex))) {
                    endIndex--;
                }
            }
            result.append("║ " + String.format("%-49s", text.substring(index, endIndex)) + " ║\n");
            index = endIndex;
        }
        return result.toString();
    }
}
