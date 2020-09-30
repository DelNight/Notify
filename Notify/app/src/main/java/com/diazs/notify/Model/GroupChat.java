package com.diazs.notify.Model;

public class GroupChat {
    private int idGroup;
    private String namaGroup;
    private String fotoGroup;
    private String deskripsi;

    public GroupChat(){}

    public GroupChat(int idGroup, String namaGroup, String fotoGroup, String deskripsi) {
        this.idGroup = idGroup;
        this.namaGroup = namaGroup;
        this.fotoGroup = fotoGroup;
        this.deskripsi = deskripsi;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getNamaGroup() {
        return namaGroup;
    }

    public void setNamaGroup(String namaGroup) {
        this.namaGroup = namaGroup;
    }

    public String getFotoGroup() {
        return fotoGroup;
    }

    public void setFotoGroup(String fotoGroup) {
        this.fotoGroup = fotoGroup;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
