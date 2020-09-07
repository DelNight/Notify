package com.diazs.notify.Model;

public class Agregate extends User {
    private String idAgregate;
    private int noUrut;
    private String visi;
    private String misi;
    private String profil;

    public String getIdAgregate() {
        return idAgregate;
    }

    public void setIdAgregate(String idAgregate) {
        this.idAgregate = idAgregate;
    }

    public int getNoUrut() {
        return noUrut;
    }

    public void setNoUrut(int noUrut) {
        this.noUrut = noUrut;
    }

    public String getVisi() {
        return visi;
    }

    public void setVisi(String visi) {
        this.visi = visi;
    }

    public String getMisi() {
        return misi;
    }

    public void setMisi(String misi) {
        this.misi = misi;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }
}
