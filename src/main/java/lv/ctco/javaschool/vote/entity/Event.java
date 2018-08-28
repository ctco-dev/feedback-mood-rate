package lv.ctco.javaschool.vote.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private String eventName;
    private String date;
    private LocalDate voteDeadlineDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getVoteDeadlineDate() {
        return voteDeadlineDate;
    }

    public void setVoteDeadlineDate(LocalDate voteDeadlineDate) {
        this.voteDeadlineDate = voteDeadlineDate;
    }
}
