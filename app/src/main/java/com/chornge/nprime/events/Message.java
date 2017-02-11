package com.chornge.nprime.events;

/**
 * Comments and posts in Events will be instances of this Message class
 */
public class Message {
    private String author;
    private String comment;
    private String timeStamp;

    public Message() {
        //blank default constructor
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
