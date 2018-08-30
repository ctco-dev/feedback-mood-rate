package lv.ctco.javaschool.vote.boundary;

import jdk.nashorn.internal.runtime.logging.Logger;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.*;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import lv.ctco.javaschool.vote.entity.dto.EventDto;
import lv.ctco.javaschool.vote.entity.dto.EventDtoList;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
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
    public EventDtoList getIncompleteEvents() {
        User currentUser = userStore.getCurrentUser();
        List<Event> eventList = voteStore.getIncompleteEventList(currentUser);
        List<EventDto> eventDtos = new ArrayList<>();

        eventList.forEach(ev -> {
            EventDto evDto = new EventDto();
            evDto.setEventName(ev.getEventName());
            evDto.setDate(ev.getDate());
            eventDtos.add(evDto);
        });
        EventDtoList evList = new EventDtoList();
        evList.setEventDtoList(eventDtos);
        return evList;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/checkDay")
    public boolean checkDay() {
        User currentUser = userStore.getCurrentUser();
        Optional<DailyVote> day = voteStore.getCurrentVoteDate(currentUser, LocalDate.now());
        return day.isPresent();
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/checkSubmit")
    public Response checkSubmitDailyVote(DailyVoteDto feedback){
        if(checkDay()){
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        } else if (feedback.getMood() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            submitDailyVote(feedback);
            return Response.status(Response.Status.CREATED).build();
        }
    }

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
