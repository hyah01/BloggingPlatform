package org.example;

import java.time.Instant;

public class Comment {
    private String content;
    private String author;
    private Instant timestamp;

    // Constructors, getters, and setters

    public Comment(String content, String author, Instant timestamp) {
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ %-49s ║\n", "Comment"));
        sb.append("╠═══════════════════════════════════════════════════╣\n");
        sb.append("║ Content:                                          ║\n");
        sb.append(wrapText(content, 50));
        sb.append(String.format("║ %-49s ║\n", "Author: " + author));
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