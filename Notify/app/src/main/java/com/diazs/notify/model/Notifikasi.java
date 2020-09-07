package com.diazs.notify.model;

import android.content.Intent;

public class Notifikasi {
    private int idNotifikasi;
    private String judulNotivikasi;
    private int isiNotifikasi;

    public void Notifikasi(){

    }

    public Notifikasi(Integer idNotifikasi, String judulNotivikasi, Integer isiNotifikasi) {
        this.idNotifikasi = idNotifikasi;
        this.judulNotivikasi = judulNotivikasi;
        this.isiNotifikasi = isiNotifikasi;
    }

    public Integer getIdNotifikasi() {
        return idNotifikasi;
    }

    public void setIdNotifikasi(Integer idNotifikasi) {
        this.idNotifikasi = idNotifikasi;
    }

    public String getJudulNotivikasi() {
        return judulNotivikasi;
    }

    public void setJudulNotivikasi(String judulNotivikasi) {
        this.judulNotivikasi = judulNotivikasi;
    }

    public Integer getIsiNotifikasi() {
        return isiNotifikasi;
    }

    public void setIsiNotifikasi(Integer isiNotifikasi) {
        this.isiNotifikasi = isiNotifikasi;
    }
}
