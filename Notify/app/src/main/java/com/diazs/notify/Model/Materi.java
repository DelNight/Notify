package com.diazs.notify.Model;

import java.io.InputStream;

public class Materi {
    private int idMateri;
    private String judulMateri;
    private String deskripsiMateri;
    private String kategoriMateri;
    private InputStream videoMateri;
    private InputStream pdfMateri;
    private InputStream wordMateri;
    private InputStream fotoMateri;
    private String authorMateri;

    public Materi() {}

    public Materi(int idMateri, String judulMateri, String deskripsiMateri, String kategoriMateri, InputStream videoMateri, InputStream pdfMateri, InputStream wordMateri, InputStream fotoMateri, String authorMateri) {
        this.idMateri = idMateri;
        this.judulMateri = judulMateri;
        this.deskripsiMateri = deskripsiMateri;
        this.kategoriMateri = kategoriMateri;
        this.videoMateri = videoMateri;
        this.pdfMateri = pdfMateri;
        this.wordMateri = wordMateri;
        this.fotoMateri = fotoMateri;
        this.authorMateri = authorMateri;
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

    public InputStream getVideoMateri() {
        return videoMateri;
    }

    public void setVideoMateri(InputStream videoMateri) {
        this.videoMateri = videoMateri;
    }

    public InputStream getPdfMateri() {
        return pdfMateri;
    }

    public void setPdfMateri(InputStream pdfMateri) {
        this.pdfMateri = pdfMateri;
    }

    public InputStream getWordMateri() {
        return wordMateri;
    }

    public void setWordMateri(InputStream wordMateri) {
        this.wordMateri = wordMateri;
    }

    public InputStream getFotoMateri() {
        return fotoMateri;
    }

    public void setFotoMateri(InputStream fotoMateri) {
        this.fotoMateri = fotoMateri;
    }

    public String getAuthorMateri() {
        return authorMateri;
    }

    public void setAuthorMateri(String authorMateri) {
        this.authorMateri = authorMateri;
    }
}

