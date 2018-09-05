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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (eventName != null ? !eventName.equals(event.eventName) : event.eventName != null) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        return voteDeadlineDate != null ? voteDeadlineDate.equals(event.voteDeadlineDate) : event.voteDeadlineDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (voteDeadlineDate != null ? voteDeadlineDate.hashCode() : 0);
        return result;
    }
}
