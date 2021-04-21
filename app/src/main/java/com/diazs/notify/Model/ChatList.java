package com.diazs.notify.Model;

import java.util.ArrayList;

public class ChatList {
    private String id;
    private String listChatOwner;
    private ArrayList<ChatMessage> listChatMessages;

    public ChatList(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListChatOwner() {
        return listChatOwner;
    }

    public void setListChatOwner(String listChatOwner) {
        this.listChatOwner = listChatOwner;
    }

    public ArrayList<ChatMessage> getListChatMessages() {
        return listChatMessages;
    }

    public void setListChatMessages(ArrayList<ChatMessage> listChatMessages) {
        this.listChatMessages = listChatMessages;
    }
}
