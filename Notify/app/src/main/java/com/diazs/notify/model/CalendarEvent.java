package com.diazs.notify.model;

public class CalendarEvent {
    private int idCalendarEvent;
    private String judulCalendarEvent;
    private int deskripsiCalendarEvent;
    private boolean privatEvent;

    public CalendarEvent() {
    }


    public CalendarEvent(Integer idCalendarEvent, String judulCalendarEvent, Integer deskripsiCalendarEvent, boolean privatEvent) {
        this.idCalendarEvent = idCalendarEvent;
        this.judulCalendarEvent = judulCalendarEvent;
        this.deskripsiCalendarEvent = deskripsiCalendarEvent;
        this.privatEvent = privatEvent;
    }

    public Integer getIdCalendarEvent() {
        return idCalendarEvent;
    }

    public void setIdCalendarEvent(Integer idCalendarEvent) {
        this.idCalendarEvent = idCalendarEvent;
    }

    public String getJudulCalendarEvent() {
        return judulCalendarEvent;
    }

    public void setJudulCalendarEvent(String judulCalendarEvent) {
        this.judulCalendarEvent = judulCalendarEvent;
    }

    public Integer getDeskripsiCalendarEvent() {
        return deskripsiCalendarEvent;
    }

    public void setDeskripsiCalendarEvent(Integer deskripsiCalendarEvent) {
        this.deskripsiCalendarEvent = deskripsiCalendarEvent;
    }

    public boolean isPrivatEvent() {
        return privatEvent;
    }

    public void setPrivatEvent(boolean privatEvent) {
        this.privatEvent = privatEvent;
    }
}
