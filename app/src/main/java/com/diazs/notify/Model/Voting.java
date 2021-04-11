package com.diazs.notify.Model;

import java.util.ArrayList;
import java.util.Date;

public class Voting {
    private String idVoting;
    private String votingMaker;
    private String deskripsiVoting;
    private String judulPosting;
    private String jenisVoting;
    private String kadaluwarsa;
    private ArrayList<Agregate> listAgregates;
    private long createdAt;

    public Voting() {
        listAgregates = new ArrayList<>();
    }

    public Voting(String idVoting, String votingMaker, String deskripsiVoting, String judulPosting, String jenisVoting, String kadaluwarsa, ArrayList<Agregate> listAgregates) {
        this.idVoting = idVoting;
        this.votingMaker = votingMaker;
        this.deskripsiVoting = deskripsiVoting;
        this.judulPosting = judulPosting;
        this.jenisVoting = jenisVoting;
        this.kadaluwarsa = kadaluwarsa;
        this.listAgregates = listAgregates;
    }

    public String getIdVoting() {
        return idVoting;
    }

    public void setIdVoting(String idVoting) {
        this.idVoting = idVoting;
    }

    public String getVotingMaker() {
        return votingMaker;
    }

    public void setVotingMaker(String votingMaker) {
        this.votingMaker = votingMaker;
    }

    public String getDeskripsiVoting() {
        return deskripsiVoting;
    }

    public void setDeskripsiVoting(String deskripsiVoting) {
        this.deskripsiVoting = deskripsiVoting;
    }

    public String getJudulPosting() {
        return judulPosting;
    }

    public void setJudulPosting(String judulPosting) {
        this.judulPosting = judulPosting;
    }

    public String getJenisVoting() {
        return jenisVoting;
    }

    public void setJenisVoting(String jenisVoting) {
        this.jenisVoting = jenisVoting;
    }

    public String getKadaluwarsa() {
        return kadaluwarsa;
    }

    public void setKadaluwarsa(String kadaluwarsa) {
        this.kadaluwarsa = kadaluwarsa;
    }

    public ArrayList<Agregate> getListAgregates() {
        return listAgregates;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setListAgregates(ArrayList<Agregate> listAgregates) {
        this.listAgregates = listAgregates;
    }
}
