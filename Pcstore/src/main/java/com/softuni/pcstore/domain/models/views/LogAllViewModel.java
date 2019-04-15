package com.softuni.pcstore.domain.models.views;

import java.time.LocalDateTime;
public class LogAllViewModel {

    private String user;
    private String event;
    private LocalDateTime dateTime;

    public LogAllViewModel() {
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
