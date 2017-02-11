package com.chornge.nprime.events;

public class Messages {
    private String message;
    private String author;

    public Messages(String author, String message) {
        this.author = author;
        this.message = message;
    }

    @SuppressWarnings("unused")
    private Messages() {
        //Blank default constructor - require for Firebase
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
