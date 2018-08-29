package lv.ctco.javaschool.vote.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private String eventName;
    private LocalDate date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getVoteDeadlineDate() {
        return voteDeadlineDate;
    }

    public void setVoteDeadlineDate(LocalDate voteDeadlineDate) {
        this.voteDeadlineDate = voteDeadlineDate;
    }
}
