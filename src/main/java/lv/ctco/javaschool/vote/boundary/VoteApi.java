package lv.ctco.javaschool.vote.boundary;

import jdk.nashorn.internal.runtime.logging.Logger;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import lv.ctco.javaschool.vote.entity.dto.DateDto;
import lv.ctco.javaschool.vote.entity.dto.EventDto;
import lv.ctco.javaschool.vote.entity.dto.EventDtoList;
import lv.ctco.javaschool.vote.entity.dto.StatisticsDto;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

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

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/getStatistics")
    public List<StatisticsDto> getStatistics(DateDto dateDto) {

        //TODO: need to separate day stats and week stats
        LocalDate selectedDate = dateDto.getDate();
        String week = dateDto.getWeek().replace("W","");

        System.out.println(week);
        LocalDate weekFirstDate =
                LocalDate.parse(week,
                        new DateTimeFormatterBuilder().appendPattern("YYYY-w")
                                .parseDefaulting(WeekFields.ISO.dayOfWeek(), 1)
                                .toFormatter());
        LocalDate weekLastDate = weekFirstDate.plusDays(6);


        //Select Vote Per One Day
        List<DailyVote> dailyVotesList = voteStore.getDayDailyVote(selectedDate);

        //Select Vote Per One Week
        //List<DailyVote> dailyVotesList = voteStore.getWeekDailyVote(weekFirstDate,weekLastDate);

        List<StatisticsDto> statsList = new ArrayList<>();

        for (DailyVote item: dailyVotesList) {
            StatisticsDto currentDailyVote = new StatisticsDto();
            currentDailyVote.setMood(item.getMood());
            currentDailyVote.setComment(item.getComment());
            statsList.add(currentDailyVote);
        }
        return statsList;
    }
}
