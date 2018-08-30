package lv.ctco.javaschool.vote.entity.dto;

import java.time.LocalDate;

public class DateDto {
    private LocalDate date;
    private String week;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
