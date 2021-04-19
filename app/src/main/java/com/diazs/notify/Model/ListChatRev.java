package com.diazs.notify.Model;

public class ListChatRev {
    private String username;
    private String message;
    private int photo;

    public ListChatRev(String username, String message, int photo) {
        this.username = username;
        this.message = message;
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTxmessage() {
        return message;
    }

    public void setTxmessage(String txmessage) {
        this.message = txmessage;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
