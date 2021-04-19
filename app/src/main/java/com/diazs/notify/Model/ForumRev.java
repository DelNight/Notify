package com.diazs.notify.Model;

public class ForumRev {
    private String postTitle;
    private String postDesc;
    private String username;

    public ForumRev(String postTitle, String postDesc, String username) {
        this.postTitle = postTitle;
        this.postDesc = postDesc;
        this.username = username;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
