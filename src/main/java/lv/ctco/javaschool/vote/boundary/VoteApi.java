package lv.ctco.javaschool.vote.boundary;

import jdk.nashorn.internal.runtime.logging.Logger;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import lv.ctco.javaschool.vote.entity.dto.DateDto;
import lv.ctco.javaschool.vote.entity.dto.EventDto;
import lv.ctco.javaschool.vote.entity.dto.EventVoteDto;
import lv.ctco.javaschool.vote.entity.dto.StatisticsDto;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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
            if (checkTodayDate(ev)) {
                events.add(e);
            }
        });
        return events;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/eventVote")
    public List<EventVoteDto> getEventVoteList() {
        User currentUser = userStore.getCurrentUser();
        List<EventVote> eventVoteList = voteStore.getAllEventVotes(currentUser);
        List<EventVoteDto> eventVoteDtos = new ArrayList<>();

        eventVoteList.forEach(ev -> {
            EventVoteDto e = new EventVoteDto();
            e.setUsername(ev.getUser().getUsername());
            e.setEventName(ev.getEvent().getEventName());
            e.setMood(ev.getMood());
            e.setComment(ev.getComment());
            if (checkTodayDate(ev)) {
                eventVoteDtos.add(e);
            }
        });
        return eventVoteDtos;
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/submitEventVote")
    public Response submitEventVote(EventVoteDto feedback) {
        if (feedback.getEventName() == null || feedback.getEventName().length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error").build();
        }
        mergeEventVote(feedback);
        return Response.ok().build();
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
        }
        submitDailyVote(feedback);
        return Response.status(Response.Status.CREATED).build();
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

    public boolean checkTodayDate(EventVote ev) {
        LocalDate todayDate = LocalDate.now();
        LocalDate eventDate = ev.getEvent().getDate();
        LocalDate voteDeadlineDate = ev.getEvent().getVoteDeadlineDate();
        return (todayDate.isBefore(voteDeadlineDate) || todayDate.isEqual(voteDeadlineDate))
                && (todayDate.isAfter(eventDate) || todayDate.isEqual(eventDate));
    }

    public void mergeEventVote(EventVoteDto feedback) {
        User currentUser = userStore.getCurrentUser();
        Event currentEvent = voteStore.getEventByEventName(feedback.getEventName());
        EventVote eventVote = voteStore.getEventVoteByUserIdEventId(currentUser, currentEvent);

        eventVote.setMood(feedback.getMood());
        eventVote.setComment(feedback.getComment());
        em.merge(eventVote);
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/getDailyStatistics")
    public List<StatisticsDto> getStatistics(DateDto dateDto) {
        List<DailyVote> dailyVotesList;
        List<StatisticsDto> statsList = new ArrayList<>();

        if ( dateDto.getDate() != null) {
            LocalDate selectedDate = dateDto.getDate();
            dailyVotesList = voteStore.getDayDailyVote(selectedDate);
            fillStatisticsDto(dailyVotesList,statsList);
        } else if (dateDto.getWeek() != null) {
            String week = dateDto.getWeek().replace("W","");

            LocalDate weekFirstDate =
                    LocalDate.parse(week,
                            new DateTimeFormatterBuilder().appendPattern("YYYY-w")
                                    .parseDefaulting(WeekFields.ISO.dayOfWeek(), 1)
                                    .toFormatter());
            LocalDate weekLastDate = weekFirstDate.plusDays(6);
            dailyVotesList = voteStore.getWeekDailyVote(weekFirstDate,weekLastDate);
            fillStatisticsDto(dailyVotesList,statsList);
        }

        return statsList;
    }
    private void fillStatisticsDto(List<DailyVote> dailyVoteList, List<StatisticsDto> statisticsDtoList) {
        for (DailyVote item: dailyVoteList) {
            StatisticsDto currentDailyVote = new StatisticsDto();
            currentDailyVote.setMood(item.getMood());
            currentDailyVote.setComment(item.getComment());
            statisticsDtoList.add(currentDailyVote);
        }
    }
}
