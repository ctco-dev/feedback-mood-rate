package lv.ctco.javaschool.vote.entity.dto;

import lv.ctco.javaschool.vote.entity.MoodStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class EventVoteDto {
    private String username;
    private String eventName;

    @Enumerated(EnumType.STRING)
    private MoodStatus mood;

    private String comment;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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
