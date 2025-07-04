package com.cognixia.fh.capstone.model;

public class Book {

    private int id;
    private String name;
    private String author;
    private String progress;
    
    public Book(int id, String name, String author, String progress) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", author=" + author + ", progress=" + progress + "]";
    }

}
