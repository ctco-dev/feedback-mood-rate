package lv.ctco.javaschool.vote.entity;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class EventVoteDto {
    private Event event;
    private User user;

    @Enumerated(EnumType.STRING)
    private MoodStatus mood;

    private String comment;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MoodStatus getMood() {
        return mood;
    }

    public void setMood(MoodStatus mood) {
        this.mood = mood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
