package com.diazs.notify.Model;

public class Voting {
    private String idVoting;
    private User votingMaker;
    private String deskripsiVoting;
    private String jenisVoting;

    public Voting() {
    }

    public Voting(String idVoting, User votingMaker, String deskripsiVoting, String jenisVoting) {
        this.idVoting = idVoting;
        this.votingMaker = votingMaker;
        this.deskripsiVoting = deskripsiVoting;
        this.jenisVoting = jenisVoting;
    }

    public String getIdVoting() {
        return idVoting;
    }

    public void setIdVoting(String idVoting) {
        this.idVoting = idVoting;
    }

    public User getVotingMaker() {
        return votingMaker;
    }

    public void setVotingMaker(User votingMaker) {
        this.votingMaker = votingMaker;
    }

    public String getDeskripsiVoting() {
        return deskripsiVoting;
    }

    public void setDeskripsiVoting(String deskripsiVoting) {
        this.deskripsiVoting = deskripsiVoting;
    }

    public String getJenisVoting() {
        return jenisVoting;
    }

    public void setJenisVoting(String jenisVoting) {
        this.jenisVoting = jenisVoting;
    }
}