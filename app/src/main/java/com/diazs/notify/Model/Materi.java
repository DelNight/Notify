package com.diazs.notify.Model;

import android.net.Uri;

import java.io.InputStream;

public class Materi {
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
}