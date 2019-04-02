package com.softuni.pcstore.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "logs")
public class Log extends BaseEntity{
    private User user;
    private String event;
    private LocalDateTime dateTime;

    public Log() {
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "event", columnDefinition = "text")
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Column(name = "date_time", nullable = false, updatable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
