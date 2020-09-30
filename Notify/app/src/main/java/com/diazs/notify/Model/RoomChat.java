package com.diazs.notify.Model;

public class RoomChat {
    private int idRoom;
    private User pengirim;
    private User penerima;
    private GroupChat grupPenerima;

    public RoomChat(){}

    public RoomChat(int idRoom, User pengirim, User penerima, GroupChat grupPenerima) {
        this.idRoom = idRoom;
        this.pengirim = pengirim;
        this.penerima = penerima;
        this.grupPenerima = grupPenerima;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public User getPengirim() {
        return pengirim;
    }

    public void setPengirim(User pengirim) {
        this.pengirim = pengirim;
    }

    public User getPenerima() {
        return penerima;
    }

    public void setPenerima(User penerima) {
        this.penerima = penerima;
    }

    public GroupChat getGrupPenerima() {
        return grupPenerima;
    }

    public void setGrupPenerima(GroupChat grupPenerima) {
        this.grupPenerima = grupPenerima;
    }
}
