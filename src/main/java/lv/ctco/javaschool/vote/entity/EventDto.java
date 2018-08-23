package lv.ctco.javaschool.vote.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import java.time.LocalDate;

public class EventDto {
    private User user;
    private String eventName;
    private LocalDate date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
