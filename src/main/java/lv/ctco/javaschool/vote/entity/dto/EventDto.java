package lv.ctco.javaschool.vote.entity.dto;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class EventDto {
    private String eventName;
    private String date;
    private String deadlineDate;

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
