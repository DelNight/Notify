package com.diazs.notify.Model;

import android.net.Uri;

import java.io.InputStream;
import java.util.Date;

public class Comment {
    private String idComment;
    private Mading mading;
    private User commenter;
    private Uri file;
    private String kategori;
    private String isiKomentar;
    private String waktuDikirim;

    public Comment() {
    }

    public Comment(String idComment, Mading mading, User commenter, Uri file, String kategori, String isiKomentar, String waktuDikirim) {
        this.idComment = idComment;
        this.mading = mading;
        this.commenter = commenter;
        this.file = file;
        this.kategori = kategori;
        this.isiKomentar = isiKomentar;
        this.waktuDikirim = waktuDikirim;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public Mading getMading() {
        return mading;
    }

    public void setMading(Mading mading) {
        this.mading = mading;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Uri getFile() {
        return file;
    }

    public void setFile(Uri file) {
        this.file = file;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getIsiKomentar() {
        return isiKomentar;
    }

    public void setIsiKomentar(String isiKomentar) {
        this.isiKomentar = isiKomentar;
    }

    public String getWaktuDikirim() {
        return waktuDikirim;
    }

    public void setWaktuDikirim(String waktuDikirim) {
        this.waktuDikirim = waktuDikirim;
    }
}
