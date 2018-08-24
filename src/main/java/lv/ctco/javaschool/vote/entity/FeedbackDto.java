package lv.ctco.javaschool.vote.entity;

public class FeedbackDto {
    private int mood;
    private String comment;

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
