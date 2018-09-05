package lv.ctco.javaschool.vote.boundary;

import jdk.nashorn.internal.runtime.logging.Logger;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.EventStore;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;
import lv.ctco.javaschool.vote.entity.dto.*;

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
    @Inject
    private ResponseWrapper wrapper;
    @Inject
    private EventStore eventStore;

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/event")
    public List<EventDto> getEventsByCurrentUser() {
        User currentUser = userStore.getCurrentUser();
        List<EventVote> eventVoteList = voteStore.getEventVoteByUserId(currentUser);
        List<EventDto> events = new ArrayList<>();

        eventVoteList.forEach(ev -> {
            if (checkTodayDate(ev)) {
                EventDto e = new EventDto();
                e.setEventName(ev.getEvent().getEventName());
                events.add(e);
            }
        });
        return events;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/allEvent")
    public List<EventDto> getAllEvents() {
        List<Event> eventList = voteStore.getAllEvents();
        List<EventDto> events = new ArrayList<>();

        eventList.forEach(el -> {
            EventDto e = new EventDto();
            e.setEventName(el.getEventName());
            events.add(e);
        });
        return events;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/allEventVote")
    public List<EventVoteDto> getAllEventVotes() {
        List<EventVote> eventVoteList = voteStore.getAllEventVotes();
        List<EventVoteDto> eventVoteDtos = new ArrayList<>();

        eventVoteList.forEach(ev -> {
            EventVoteDto e = new EventVoteDto();
            e.setEventName(ev.getEvent().getEventName());
            e.setMood(ev.getMood());
            e.setComment(ev.getComment());
            eventVoteDtos.add(e);
        });
        return eventVoteDtos;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/eventVote")
    public List<EventVoteDto> getEventVoteList() {
        User currentUser = userStore.getCurrentUser();
        List<EventVote> eventVoteList = voteStore.getAllEventVotesByUser(currentUser);
        List<EventVoteDto> eventVoteDtos = new ArrayList<>();

        eventVoteList.forEach(ev -> {
            if (checkTodayDate(ev)) {
                EventVoteDto e = new EventVoteDto();
                e.setUsername(ev.getUser().getUsername());
                e.setEventName(ev.getEvent().getEventName());
                e.setMood(ev.getMood());
                e.setComment(ev.getComment());
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
    public Response checkSubmitDailyVote(DailyVoteDto feedback) {
        if (checkDay()) {
            return wrapper.getMethodNotAllowedResponse();
        } else if (feedback.getMood() == null) {
            return wrapper.getBadRequestResponse();
        }
        submitDailyVote(feedback);
        return wrapper.getCreatedResponse();
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

        if (dateDto.getDate() != null && dateDto.getWeek() == null) {
            LocalDate selectedDate = dateDto.getDate();
            dailyVotesList = voteStore.getDayDailyVote(selectedDate);
            fillStatisticsDto(dailyVotesList, statsList);
        } else if (dateDto.getWeek() != null && dateDto.getDate() == null) {
            String week = dateDto.getWeek().replace("W", "");

            LocalDate weekFirstDate =
                    LocalDate.parse(week,
                            new DateTimeFormatterBuilder().appendPattern("YYYY-w")
                                    .parseDefaulting(WeekFields.ISO.dayOfWeek(), 1)
                                    .toFormatter());
            LocalDate weekLastDate = weekFirstDate.plusDays(6);
            dailyVotesList = voteStore.getWeekDailyVote(weekFirstDate, weekLastDate);
            fillStatisticsDto(dailyVotesList, statsList);
        }

        return statsList;
    }

    public void fillStatisticsDto(List<DailyVote> dailyVoteList, List<StatisticsDto> statisticsDtoList) {
        for (DailyVote item : dailyVoteList) {
            StatisticsDto currentDailyVote = new StatisticsDto();
            currentDailyVote.setMood(item.getMood());
            currentDailyVote.setComment(item.getComment());
            statisticsDtoList.add(currentDailyVote);
        }
    }
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/createEvent")
    public void saveEvent(EventDto eventDto){
        eventStore.saveNewEvent(eventDto);
        createEventVote();
    }
    public void createEventVote(){
        List<User> userList = userStore.getAllUsers();
        for (User user:userList) {
            eventStore.createNewEventVote(voteStore.getLatestEvent(),user);
        }
    }
}
