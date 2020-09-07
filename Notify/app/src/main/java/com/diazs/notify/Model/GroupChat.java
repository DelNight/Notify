package com.diazs.notify.Model;

import java.io.InputStream;

public class GroupChat {
    private int idGroup;
    private String namaGroup;
    private InputStream fotoGroup;

    public GroupChat(){}

    public GroupChat(int idGroup, String namaGroup, InputStream fotoGroup) {
        this.idGroup = idGroup;
        this.namaGroup = namaGroup;
        this.fotoGroup = fotoGroup;
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

    public InputStream getFotoGroup() {
        return fotoGroup;
    }

    public void setFotoGroup(InputStream fotoGroup) {
        this.fotoGroup = fotoGroup;
    }
}
