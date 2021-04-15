package com.diazs.notify.Model;

import android.os.Parcel;

public class Candidate extends User{
    private String idCandidate;
    private int noUrut;
    private String visi;
    private String misi;
    private String profil;
    private String urlFoto;
    private int jumlahSuara;
    private String idVoting;

    public Candidate(){

    }

    protected Candidate(Parcel in) {
        nama = in.readString();
        profil = in.readString();
        idCandidate = in.readString();
        noUrut = in.readInt();
        visi = in.readString();
        misi = in.readString();
        profil = in.readString();
        jumlahSuara = in.readInt();
        idVoting = in.readString();
        urlFoto = in.readString();
    }

    public static final Creator<Candidate> CREATOR = new Creator<Candidate>() {
        @Override
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        @Override
        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

    public String getIdVoting() {
        return idVoting;
    }

    public void setIdVoting(String idVoting) {
        this.idVoting = idVoting;
    }

    public int getJumlahSuara() {
        return jumlahSuara;
    }

    public void setJumlahSuara(int jumlahSuara) {
        this.jumlahSuara = jumlahSuara;
    }

    public String getIdCandidate() {
        return idCandidate;
    }

    public void setIdCandidate(String idCandidate) {
        this.idCandidate = idCandidate;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
        parcel.writeString(profil);
        parcel.writeString(idCandidate);
        parcel.writeInt(noUrut);
        parcel.writeString(visi);
        parcel.writeString(misi);
        parcel.writeString(profil);
        parcel.writeInt(jumlahSuara);
        parcel.writeString(idVoting);
        parcel.writeString(urlFoto);
    }
}
