// model/DiscussionPost.java
package org.example.model;

public class DiscussionPost {
    private int id;
    private String title;
    private String author;
    private String content;

    public DiscussionPost(int id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public DiscussionPost(String title, String author, String content) {
        this(0, title, author, content);
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }

    public void setId(int id) { this.id = id; }
}
