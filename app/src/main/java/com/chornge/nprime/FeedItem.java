package com.chornge.nprime;

class FeedItem {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String thumbNail;

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getLink() {
        return link;
    }

    void setLink(String link) {
        this.link = link;
    }

    String getPubDate() {
        return pubDate;
    }

    void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}