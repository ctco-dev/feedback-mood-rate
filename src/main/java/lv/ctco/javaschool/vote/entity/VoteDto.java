package lv.ctco.javaschool.vote.entity;

public class VoteDto {
    private VoteStatus status;
    private boolean eventStatus;
    private boolean dayStatus;

    public VoteStatus getStatus() {
        return status;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }

    public boolean isEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(boolean eventStatus) {
        this.eventStatus = eventStatus;
    }

    public boolean isDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(boolean dayStatus) {
        this.dayStatus = dayStatus;
    }
}
