package com.diazs.notify.Model;

import android.net.Uri;

import java.io.InputStream;

public class Mading {
    private int idMading;
    private User poster;
    private String kategoriMading;
    private String judulMading;
    private Uri gambarMading;
    private String deskripsiMading;
    private String contactPersonMading;
    private String tanggalUpload;

    public Mading(){}

    public Mading(int idMading, User poster, String kategoriMading, String judulMading, Uri gambarMading, String deskripsiMading, String contactPersonMading, String tanggalUpload) {
        this.idMading = idMading;
        this.poster = poster;
        this.kategoriMading = kategoriMading;
        this.judulMading = judulMading;
        this.gambarMading = gambarMading;
        this.deskripsiMading = deskripsiMading;
        this.contactPersonMading = contactPersonMading;
        this.tanggalUpload = tanggalUpload;
    }

    public int getIdMading() {
        return idMading;
    }

    public void setIdMading(int idMading) {
        this.idMading = idMading;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public String getKategoriMading() {
        return kategoriMading;
    }

    public void setKategoriMading(String kategoriMading) {
        this.kategoriMading = kategoriMading;
    }

    public String getJudulMading() {
        return judulMading;
    }

    public void setJudulMading(String judulMading) {
        this.judulMading = judulMading;
    }

    public Uri getGambarMading() {
        return gambarMading;
    }

    public void setGambarMading(Uri gambarMading) {
        this.gambarMading = gambarMading;
    }

    public String getDeskripsiMading() {
        return deskripsiMading;
    }

    public void setDeskripsiMading(String deskripsiMading) {
        this.deskripsiMading = deskripsiMading;
    }

    public String getContactPersonMading() {
        return contactPersonMading;
    }

    public void setContactPersonMading(String contactPersonMading) {
        this.contactPersonMading = contactPersonMading;
    }

    public String getTanggalUpload() {
        return tanggalUpload;
    }

    public void setTanggalUpload(String tanggalUpload) {
        this.tanggalUpload = tanggalUpload;
    }
}
