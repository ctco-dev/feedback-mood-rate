package lv.ctco.javaschool.vote.entity;

/**
 * Created by kristaps.lipsha01 on 8/27/2018.
 */
public class EventFeedbackDto {
    private int eventId;
    private int mood;
    private String comment;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
