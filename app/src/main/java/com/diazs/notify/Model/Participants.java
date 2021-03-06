package com.diazs.notify.Model;

import java.security.acl.Group;

public class Participants {
    private int idParticipants;
    private User angggota;
    private Group grup;

    public Participants(){}

    public Participants(int idParticipants, User angggota, Group grup) {
        this.idParticipants = idParticipants;
        this.angggota = angggota;
        this.grup = grup;
    }

    public int getIdParticipants() {
        return idParticipants;
    }

    public void setIdParticipants(int idParticipants) {
        this.idParticipants = idParticipants;
    }

    public User getAngggota() {
        return angggota;
    }

    public void setAngggota(User angggota) {
        this.angggota = angggota;
    }

    public Group getGrup() {
        return grup;
    }

    public void setGrup(Group grup) {
        this.grup = grup;
    }
}
