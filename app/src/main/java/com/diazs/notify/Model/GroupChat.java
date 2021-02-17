package com.diazs.notify.Model;

import android.net.Uri;

import java.io.InputStream;

public class GroupChat {
    private int idGroup;
    private String namaGroup;
    private Uri fotoGroup;
    private String deskripsGroup;

    public GroupChat(){}

    public GroupChat(int idGroup, String namaGroup, Uri fotoGroup, String deskripsGroup) {
        this.idGroup = idGroup;
        this.namaGroup = namaGroup;
        this.fotoGroup = fotoGroup;
        this.deskripsGroup = deskripsGroup;
    }

    public String getDeskripsGroup() {
        return deskripsGroup;
    }

    public void setDeskripsGroup(String deskripsGroup) {
        this.deskripsGroup = deskripsGroup;
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

    public Uri getFotoGroup() {
        return fotoGroup;
    }

    public void setFotoGroup(Uri fotoGroup) {
        this.fotoGroup = fotoGroup;
    }
}