package lv.ctco.javaschool.vote.entity;

import java.time.LocalDate;

public class DailyVoteDto {
    private int mood;
    private String comment;
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
