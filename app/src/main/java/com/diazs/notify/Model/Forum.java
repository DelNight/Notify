package com.diazs.notify.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Forum implements Parcelable {
    private String idForum;
    private String author;
    private String kategori;
    private String judul;
    private String deskripsi;
    private String tanggalUpload;
    private long createdAt;
    private String linkImg;

    public Forum(){

    }

    protected Forum(Parcel in) {
        idForum = in.readString();
        author = in.readString();
        kategori = in.readString();
        judul = in.readString();
        deskripsi = in.readString();
        tanggalUpload = in.readString();
        createdAt = in.readLong();
        linkImg = in.readString();
    }

    public static final Creator<Forum> CREATOR = new Creator<Forum>() {
        @Override
        public Forum createFromParcel(Parcel in) {
            return new Forum(in);
        }

        @Override
        public Forum[] newArray(int size) {
            return new Forum[size];
        }
    };

    public String getIdForum() {
        return idForum;
    }

    public void setIdForum(String idForum) {
        this.idForum = idForum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggalUpload() {
        return tanggalUpload;
    }

    public void setTanggalUpload(String tanggalUpload) {
        this.tanggalUpload = tanggalUpload;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idForum);
        parcel.writeString(author);
        parcel.writeString(kategori);
        parcel.writeString(judul);
        parcel.writeString(deskripsi);
        parcel.writeString(tanggalUpload);
        parcel.writeLong(createdAt);
        parcel.writeString(linkImg);
    }
}
