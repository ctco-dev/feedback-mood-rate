package lv.ctco.javaschool.vote.boundary;

import jdk.nashorn.internal.runtime.logging.Logger;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.EventDto;
import lv.ctco.javaschool.vote.entity.EventType;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.FeedbackDto;
import lv.ctco.javaschool.vote.entity.Vote;
import lv.ctco.javaschool.vote.entity.VoteDto;
import lv.ctco.javaschool.vote.entity.VoteStatus;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import lv.ctco.javaschool.vote.entity.dto.EventDto;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Path("/vote")
@Stateless
@Logger
public class VoteApi {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserStore userStore;
    @Inject
    private VoteStore voteStore;

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/event")
    public List<EventDto> getEvents() {
        User currentUser = userStore.getCurrentUser();
        List<EventVote> eventVoteList = voteStore.getEventVoteByUserId(currentUser);
        List<EventDto> events = new ArrayList<>();

        eventVoteList.forEach(ev -> {
            EventDto e = new EventDto();
            e.setEventName(ev.getEvent().getEventName());
            LocalDate todayDate = LocalDate.now();
            LocalDate eventDate = ev.getEvent().getDate();
            LocalDate voteDeadlineDate = ev.getEvent().getVoteDeadlineDate();
            if ((todayDate.isBefore(voteDeadlineDate) || todayDate.isEqual(voteDeadlineDate))
                    && (todayDate.isAfter(eventDate) || todayDate.isEqual(eventDate))) {
                events.add(e);
            }
        });
        return events;
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/submitDailyVote")
    public void submitDailyVote(DailyVoteDto feedback) {
        User currentUser = userStore.getCurrentUser();
        LocalDate today = LocalDate.now();

        DailyVote dailyVote = new DailyVote();
        dailyVote.setUser(currentUser);
        dailyVote.setMood(feedback.getMood());
        dailyVote.setComment(feedback.getComment());
        dailyVote.setDate(today);
        em.persist(dailyVote);
    }
}
