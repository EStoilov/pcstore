package com.softuni.pcstore.domain.models.service;

import java.time.LocalDateTime;
public class LogServiceModel {
    
    private String id;
    private String user;
    private String event;
    private LocalDateTime dateTime;

    public LogServiceModel() {
    }

    public LogServiceModel(String user, String event) {
        this.user = user;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
