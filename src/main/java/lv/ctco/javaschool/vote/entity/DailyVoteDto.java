package lv.ctco.javaschool.vote.entity;

public class DailyFeedbackDto {
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
