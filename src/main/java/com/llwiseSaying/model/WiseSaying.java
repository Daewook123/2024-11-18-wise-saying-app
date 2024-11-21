package com.llwiseSaying.model;

public class WiseSaying {

    private int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author){
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }
    public void setId() {
        this.id = id;
    }

    public String getSaying() {
        return content;
    }
    public void setSaying(String content) {
        this.content = content;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

}
