package com.diazs.notify.Model;

import java.util.Date;

public class ChatMessage {
    private int idChat;
    private String messageText;
    private User messageUser;
    private  RoomChat room;
    private long messageTime;
    private String chatImage;
    private int Readstatus;


    public ChatMessage(){}

    public ChatMessage(int idChat, String messageText, User messageUser, RoomChat room, long messageTime, String chatImage, int readstatus) {
        this.idChat = idChat;
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.room = room;
        this.chatImage = chatImage;
        this.Readstatus = readstatus;

        this.messageTime = new Date().getTime();
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public User getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(User messageUser) {
        this.messageUser = messageUser;
    }

    public RoomChat getRoom() {
        return room;
    }

    public void setRoom(RoomChat room) {
        this.room = room;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public int getReadstatus() {
        return Readstatus;
    }

    public void setReadstatus(int readstatus) {
        Readstatus = readstatus;
    }
}
