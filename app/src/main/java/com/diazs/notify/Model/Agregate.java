package com.diazs.notify.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Agregate extends User implements Parcelable {
    private String idAgregate;
    private int noUrut;
    private String visi;
    private String misi;
    private String profil;
    private int jumlahSuara;
    private String idVoting;

    public Agregate(){

    }

    protected Agregate(Parcel in) {
        idAgregate = in.readString();
        noUrut = in.readInt();
        visi = in.readString();
        misi = in.readString();
        profil = in.readString();
        jumlahSuara = in.readInt();
        idVoting = in.readString();
    }

    public static final Creator<Agregate> CREATOR = new Creator<Agregate>() {
        @Override
        public Agregate createFromParcel(Parcel in) {
            return new Agregate(in);
        }

        @Override
        public Agregate[] newArray(int size) {
            return new Agregate[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idAgregate);
        parcel.writeInt(noUrut);
        parcel.writeString(visi);
        parcel.writeString(misi);
        parcel.writeString(profil);
        parcel.writeInt(jumlahSuara);
        parcel.writeString(idVoting);
    }
}
