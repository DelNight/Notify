package com.diazs.notify.Model;

import android.net.Uri;

import java.io.InputStream;

public class Materi {
    private int idMateri;
    private String judulMateri;
    private String deskripsiMateri;
    private String kategoriMateri;
    private Uri videoMateri;
    private Uri pdfMateri;
    private Uri wordMateri;
    private Uri fotoMateri;
    private String authorMateri;
    private boolean global;

    public boolean getGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public Materi() {}

    public Materi(int idMateri, String judulMateri, String deskripsiMateri, String kategoriMateri, Uri videoMateri, Uri pdfMateri, Uri wordMateri, Uri fotoMateri, String authorMateri, boolean global) {
        this.idMateri = idMateri;
        this.judulMateri = judulMateri;
        this.deskripsiMateri = deskripsiMateri;
        this.kategoriMateri = kategoriMateri;
        this.videoMateri = videoMateri;
        this.pdfMateri = pdfMateri;
        this.wordMateri = wordMateri;
        this.fotoMateri = fotoMateri;
        this.authorMateri = authorMateri;
        this.global = global;
    }

    public int getIdMateri() {
        return idMateri;
    }

    public void setIdMateri(int idMateri) {
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
}