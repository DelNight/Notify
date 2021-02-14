package com.diazs.notify.Model;

public class Vote {
    private String idVote;
    private Voting voting;
    private User voter;
    private Agregate pilihan;

    public Vote() {
    }

    public Vote(String idVote, Voting voting, User voter, Agregate pilihan) {
        this.idVote = idVote;
        this.voting = voting;
        this.voter = voter;
        this.pilihan = pilihan;
    }

    public String getIdVote() {
        return idVote;
    }

    public void setIdVote(String idVote) {
        this.idVote = idVote;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public Agregate getPilihan() {
        return pilihan;
    }

    public void setPilihan(Agregate pilihan) {
        this.pilihan = pilihan;
    }
}
