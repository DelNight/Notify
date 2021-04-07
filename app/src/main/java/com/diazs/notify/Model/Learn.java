package com.diazs.notify.Model;

public class Learn {
    private String gambar;
    private String namaFile;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public Learn() {}

    public Learn(String gambar, String namaFile, int type){
        this.gambar = gambar;
        this.namaFile = namaFile;
        this.type = type;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

}