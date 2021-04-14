package com.diazs.notify.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.InputStream;
import java.util.Map;

public class Materi implements Parcelable {
    private String idMateri;
    private String judulMateri;
    private String deskripsiMateri;
    private String kategoriMateri;
    private Uri videoMateri;
    private Uri pdfMateri;
    private Uri wordMateri;
    private Uri fotoMateri;
    private String authorMateri;
    private boolean global;
    private String fileUrl;
    private String videoUrl;
    private String imageUrl;
    private long createdAt;

    protected Materi(Parcel in) {
        idMateri = in.readString();
        judulMateri = in.readString();
        deskripsiMateri = in.readString();
        kategoriMateri = in.readString();
        videoMateri = in.readParcelable(Uri.class.getClassLoader());
        pdfMateri = in.readParcelable(Uri.class.getClassLoader());
        wordMateri = in.readParcelable(Uri.class.getClassLoader());
        fotoMateri = in.readParcelable(Uri.class.getClassLoader());
        authorMateri = in.readString();
        global = in.readByte() != 0;
        fileUrl = in.readString();
        videoUrl = in.readString();
        imageUrl = in.readString();
        createdAt = in.readLong();
    }

    public static final Creator<Materi> CREATOR = new Creator<Materi>() {
        @Override
        public Materi createFromParcel(Parcel in) {
            return new Materi(in);
        }

        @Override
        public Materi[] newArray(int size) {
            return new Materi[size];
        }
    };

    public boolean getGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public Materi() {}

    public String getIdMateri() {
        return idMateri;
    }

    public void setIdMateri(String idMateri) {
        this.idMateri = idMateri;
    }

    public String getJudulMateri() {
        return judulMateri;
    }

    public void setJudulMateri(String judulMateri) {
        this.judulMateri = judulMateri;
    }

    public String getDeskripsiMateri() {
        return deskripsiMateri;
    }

    public void setDeskripsiMateri(String deskripsiMateri) {
        this.deskripsiMateri = deskripsiMateri;
    }

    public String getKategoriMateri() {
        return kategoriMateri;
    }

    public void setKategoriMateri(String kategoriMateri) {
        this.kategoriMateri = kategoriMateri;
    }

    public Uri getVideoMateri() {
        return videoMateri;
    }

    public void setVideoMateri(Uri videoMateri) {
        this.videoMateri = videoMateri;
    }

    public Uri getPdfMateri() {
        return pdfMateri;
    }

    public void setPdfMateri(Uri pdfMateri) {
        this.pdfMateri = pdfMateri;
    }

    public Uri getWordMateri() {
        return wordMateri;
    }

    public void setWordMateri(Uri wordMateri) {
        this.wordMateri = wordMateri;
    }

    public Uri getFotoMateri() {
        return fotoMateri;
    }

    public void setFotoMateri(Uri fotoMateri) {
        this.fotoMateri = fotoMateri;
    }

    public String getAuthorMateri() {
        return authorMateri;
    }

    public void setAuthorMateri(String authorMateri) {
        this.authorMateri = authorMateri;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idMateri);
        parcel.writeString(judulMateri);
        parcel.writeString(deskripsiMateri);
        parcel.writeString(kategoriMateri);
        parcel.writeParcelable(videoMateri, i);
        parcel.writeParcelable(pdfMateri, i);
        parcel.writeParcelable(wordMateri, i);
        parcel.writeParcelable(fotoMateri, i);
        parcel.writeString(authorMateri);
        parcel.writeByte((byte) (global ? 1 : 0));
        parcel.writeString(fileUrl);
        parcel.writeString(videoUrl);
        parcel.writeString(imageUrl);
        parcel.writeLong(createdAt);
    }
}