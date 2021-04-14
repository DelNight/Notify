package com.diazs.notify.Model;

public class Event {
    private String idEvent;
    private String eventMaker;
    private String judulEvent;
    private String deskripsi;
    private String tanggalEvent;
    private long createdAt;

    public Event(String idEvent, String eventMaker, String judulEvent, String deskripsi, String tanggalEvent, long createdAt) {
        this.idEvent = idEvent;
        this.eventMaker = eventMaker;
        this.judulEvent = judulEvent;
        this.deskripsi = deskripsi;
        this.tanggalEvent = tanggalEvent;
        this.createdAt = createdAt;
    }

    public Event(){

    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getEventMaker() {
        return eventMaker;
    }

    public void setEventMaker(String eventMaker) {
        this.eventMaker = eventMaker;
    }

    public String getJudulEvent() {
        return judulEvent;
    }

    public void setJudulEvent(String judulEvent) {
        this.judulEvent = judulEvent;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
