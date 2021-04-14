package com.diazs.notify.Model;

import android.net.Uri;

import java.io.InputStream;

public class Mading {
    private String idMading;
    private String poster;
    private String kategoriMading;
    private String judulMading;
    private String gambarMading;
    private String deskripsiMading;
    private String contactPersonMading;
    private String tanggalUpload;
    private long createdAt;

    public Mading(){}

    public Mading(String idMading, String poster, String kategoriMading, String judulMading, String gambarMading, String deskripsiMading, String contactPersonMading, String tanggalUpload, long createdAt) {
        this.idMading = idMading;
        this.poster = poster;
        this.kategoriMading = kategoriMading;
        this.judulMading = judulMading;
        this.gambarMading = gambarMading;
        this.deskripsiMading = deskripsiMading;
        this.contactPersonMading = contactPersonMading;
        this.tanggalUpload = tanggalUpload;
        this.createdAt = createdAt;
    }

    public String getIdMading() {
        return idMading;
    }

    public void setIdMading(String idMading) {
        this.idMading = idMading;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
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

    public String getGambarMading() {
        return gambarMading;
    }

    public void setGambarMading(String gambarMading) {
        this.gambarMading = gambarMading;
    }

    public void setJudulMading(String judulMading) {
        this.judulMading = judulMading;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
