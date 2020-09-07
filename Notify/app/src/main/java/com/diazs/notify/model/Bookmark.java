package com.diazs.notify.model;

public class Bookmark {
    private int idBookmark;
    private User poster;

    public Bookmark() {
    }

    public Bookmark(int idBookmark, User poster) {
        this.idBookmark = idBookmark;
        this.poster = poster;
    }

    public int getIdBookmark() {
        return idBookmark;
    }

    public void setIdBookmark(int idBookmark) {
        this.idBookmark = idBookmark;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }
}
