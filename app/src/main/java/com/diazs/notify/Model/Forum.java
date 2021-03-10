package com.diazs.notify.Model;

public class Forum {
    private String idForum;
    private User poster;
    private String kategori;
    private String judul;
    private String deskripsi;
    private String tanggalUpload;

    public String getIdForum() {
        return idForum;
    }

    public void setIdForum(String idForum) {
        this.idForum = idForum;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
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
}
