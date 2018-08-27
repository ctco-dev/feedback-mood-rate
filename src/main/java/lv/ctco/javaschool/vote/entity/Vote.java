package lv.ctco.javaschool.vote.entity;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import java.time.LocalDate;

@Entity
public class Vote {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private VoteStatus voteStatus;
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String eventName;

    private String comment;

    private Integer moodValue;

    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VoteStatus getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(VoteStatus voteStatus) {
        this.voteStatus = voteStatus;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMoodValue(DailyFeedbackDto feedback) {
        return moodValue;
    }

    public void setMoodValue(Integer moodValue) {
        this.moodValue = moodValue;
    }
}
