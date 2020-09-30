package com.diazs.notify.Model;

<<<<<<< HEAD
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
=======
import android.net.Uri;

import java.io.InputStream;

public class GroupChat {
    private int idGroup;
    private String namaGroup;
    private Uri fotoGroup;

    public GroupChat(){}

    public GroupChat(int idGroup, String namaGroup, Uri fotoGroup) {
        this.idGroup = idGroup;
        this.namaGroup = namaGroup;
        this.fotoGroup = fotoGroup;
>>>>>>> b602462f516be847972da8b7a118538f258f0480
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

<<<<<<< HEAD
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
=======
    public Uri getFotoGroup() {
        return fotoGroup;
    }

    public void setFotoGroup(Uri fotoGroup) {
        this.fotoGroup = fotoGroup;
    }
>>>>>>> b602462f516be847972da8b7a118538f258f0480
}
