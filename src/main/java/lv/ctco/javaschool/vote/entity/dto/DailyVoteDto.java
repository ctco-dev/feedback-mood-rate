package lv.ctco.javaschool.vote.entity.dto;

import lv.ctco.javaschool.vote.entity.MoodStatus;

public class DailyVoteDto {
    private MoodStatus mood;
    private String comment;

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
