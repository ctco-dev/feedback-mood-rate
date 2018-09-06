package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import lv.ctco.javaschool.vote.entity.dto.DateDto;
import lv.ctco.javaschool.vote.entity.dto.StatisticsDto;
import lv.ctco.javaschool.vote.entity.dto.EventDto;
import lv.ctco.javaschool.vote.entity.dto.EventVoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VoteApiTest {
    @Mock
    private EntityManager em;
    @Mock
    private UserStore userStore;
    @Mock
    private VoteStore voteStore;
    @Mock
    private ResponseWrapper wrapper;
    @Mock
    private Response response_METHOD_NOT_ALLOWED;
    @Mock
    private Response response_BAD_REQUEST;
    @Mock
    private Response response_CREATED;

    @InjectMocks
    private VoteApi voteApi;

    private User user1;
    private User user;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        user1 = new User();
        user1.setUsername("user1");

        user = new User();
        user.setUsername("user");
    }

    @Test
    void getEventsByCurrentUserTest() {
        Event newEvent = new Event();
        newEvent.setEventName("test");
        newEvent.setDate(LocalDate.of(2018, 8, 30));
        newEvent.setVoteDeadlineDate(LocalDate.now().plusDays(5));

        Event finishedEvent = new Event();
        finishedEvent.setEventName("test finished Event");
        finishedEvent.setDate(LocalDate.of(2018, 8, 30));
        finishedEvent.setVoteDeadlineDate(LocalDate.now().minusDays(3));

        EventVote eventVote = new EventVote();
        eventVote.setEvent(newEvent);
        eventVote.setUser(user1);
        eventVote.setMood(MoodStatus.EMPTY);

        EventVote secondEventVote = new EventVote();
        secondEventVote.setEvent(finishedEvent);
        secondEventVote.setUser(user1);
        secondEventVote.setMood(MoodStatus.SAD);

        List<EventVote> eventVoteList = new ArrayList<>();
        eventVoteList.add(secondEventVote);
        eventVoteList.add(eventVote);

        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(voteStore.getEventVoteByUserId(user1))
                .thenReturn(eventVoteList);

        List<EventDto> actual = voteApi.getEventsByCurrentUser();
        assertThat(actual.get(0).getEventName(), equalTo("test"));
    }

    @Test
    void getEventVoteListTest() {

        Event newEvent = new Event();
        newEvent.setEventName("test Event Name");
        newEvent.setDate(LocalDate.of(2018, 8, 30));
        newEvent.setVoteDeadlineDate(LocalDate.now().plusDays(5));

        Event finishedEvent = new Event();
        finishedEvent.setEventName("test finished Event");
        finishedEvent.setDate(LocalDate.of(2018, 8, 30));
        finishedEvent.setVoteDeadlineDate(LocalDate.now().minusDays(3));

        EventVote eventVote = new EventVote();
        eventVote.setEvent(newEvent);
        eventVote.setUser(user1);
        eventVote.setMood(MoodStatus.HAPPY);
        eventVote.setComment("Comment test");

        EventVote secondEventVote = new EventVote();
        secondEventVote.setEvent(finishedEvent);
        secondEventVote.setUser(user1);
        secondEventVote.setMood(MoodStatus.SAD);
        secondEventVote.setComment("Comment test");

        List<EventVote> eventVoteList = new ArrayList<>();
        eventVoteList.add(secondEventVote);
        eventVoteList.add(eventVote);

        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(voteStore.getAllEventVotesByUser(user1))
                .thenReturn(eventVoteList);

        List<EventVoteDto> actual = voteApi.getEventVoteList();
        assertThat(actual.get(0).getUsername(), equalTo("user1"));
        assertThat(actual.get(0).getEventName(), equalTo("test Event Name"));
        assertThat(actual.get(0).getMood(), equalTo(MoodStatus.HAPPY));
        assertThat(actual.get(0).getComment(), equalTo("Comment test"));
    }

    @Test
    @DisplayName("GetAllEventVotesTest: Checks if method returns correct dto value")
    void getAllEventVotesTest() {
        EventVote ev = new EventVote();
        Event event = new Event();

        event.setEventName("testEvent");
        ev.setComment("test");
        ev.setMood(MoodStatus.HAPPY);
        ev.setUser(user1);
        ev.setEvent(event);
        when(voteStore.getAllEventVotes())
                .thenReturn(Collections.singletonList(ev));

        List<EventVoteDto> test = voteApi.getAllEventVotes();
        assertThat(test.size(), equalTo(1));
        EventVoteDto testResultDto = test.get(0);
        assertThat(testResultDto.getComment(), equalTo(ev.getComment()));
        assertThat(testResultDto.getEventName(), equalTo(event.getEventName()));
        assertThat(testResultDto.getMood(), equalTo(ev.getMood()));

    }

    @Test
    @DisplayName("Check if object is already in DB")
    void checkDate() {
        DailyVote day = new DailyVote();
        day.setUser(user);
        day.setDate(LocalDate.now());

        when(userStore.getCurrentUser()).thenReturn(user);
        when(voteStore.getCurrentVoteDate(user, LocalDate.now())).thenReturn(Optional.of(day));
        boolean actual = voteApi.checkDay();

        assertThat(actual, equalTo(true));
    }

    @Test
    @DisplayName("Check if todayDate after eventDate and before voteDeadlineDate")
    void checkTodayDate() {
        EventVote eventVote = new EventVote();
        Event event = new Event();
        event.setDate(LocalDate.of(2018, 8, 30));
        event.setVoteDeadlineDate(LocalDate.of(2018, 9, 10));
        eventVote.setEvent(event);
        assertTrue(voteApi.checkTodayDate(eventVote));
    }

    @Test
    @DisplayName("Check if day statistics is selected then returned StatisticsDto of DailyVote class")
    void getStatisticsTestIfDateDtoIsNull() {
        DateDto dateDto = new DateDto();
        List<StatisticsDto> statsDto = voteApi.getStatistics(dateDto);
        assertTrue(statsDto.isEmpty());
    }

    @Test
    @DisplayName("Check if day statistics is selected then returned StatisticsDto of DailyVote class")
    void getStatisticsForOneDay() {
        DateDto dateDto = new DateDto();
        dateDto.setDate(LocalDate.of(2018, 10, 10));

        List<DailyVote> dvList = new ArrayList<>();
        List<StatisticsDto> statsDto;

        for (int i = 0; i < 5; i++) {
            dvList.add(new DailyVote());
            dvList.get(i).setId((long) i);
            dvList.get(i).setComment("Comment: " + i);
        }

        when(voteStore.getDayDailyVote(dateDto.getDate()))
                .thenReturn(dvList);

        statsDto = voteApi.getStatistics(dateDto);
        for (int i = 0; i < 5; i++) {
            assertThat(statsDto.get(i).getComment(), equalTo(dvList.get(i).getComment()));
        }
    }

    @Test
    @DisplayName("Check if week statistics is selected then returned StatisticsDto of DailyVote class")
    void getStatisticsForWeek() {
        DateDto dateDto = new DateDto();
        dateDto.setWeek("2018-1");

        List<DailyVote> dvList = new ArrayList<>();
        List<StatisticsDto> statsDto;

        for (int i = 0; i < 5; i++) {
            dvList.add(new DailyVote());
            dvList.get(i).setId((long) i);
            dvList.get(i).setComment("Comment: " + i);
        }

        when(voteStore.getWeekDailyVote(LocalDate.of(2018, 1, 1),
                LocalDate.of(2018, 1, 7)))
                .thenReturn(dvList);

        statsDto = voteApi.getStatistics(dateDto);
        for (int i = 0; i < 5; i++) {
            assertThat(statsDto.get(i).getComment(), equalTo(dvList.get(i).getComment()));
        }
    }

    @Test
    @DisplayName("Check if week and day data come to function in same time then return empty dto")
    void getStatisticsSelectedBothOptions() {
        DateDto dateDto = new DateDto();
        dateDto.setWeek("2018-1");
        dateDto.setDate(LocalDate.of(2018, 1, 1));

        List<DailyVote> dvList = new ArrayList<>();
        List<StatisticsDto> statsDto;

        for (int i = 0; i < 5; i++) {
            dvList.add(new DailyVote());
            dvList.get(i).setId((long) i);
            dvList.get(i).setComment("Comment: " + i);
        }

        when(voteStore.getWeekDailyVote(LocalDate.of(2018, 1, 1),
                LocalDate.of(2018, 1, 7)))
                .thenReturn(dvList);

        statsDto = voteApi.getStatistics(dateDto);
        assertThat(statsDto.size(), equalTo(0));
    }

    @Test
    @DisplayName("Check if no data come to function, then return empty dto")
    void getStatisticsSelectedNoOptions() {
        DateDto dateDto = new DateDto();

        List<DailyVote> dvList = new ArrayList<>();
        List<StatisticsDto> statsDto;

        for (int i = 0; i < 5; i++) {
            dvList.add(new DailyVote());
            dvList.get(i).setId((long) i);
            dvList.get(i).setComment("Comment: " + i);
        }

        when(voteStore.getWeekDailyVote(LocalDate.of(2018, 1, 1),
                LocalDate.of(2018, 1, 7)))
                .thenReturn(dvList);

        statsDto = voteApi.getStatistics(dateDto);
        assertThat(statsDto.size(), equalTo(0));
    }

    @Test
    @DisplayName("Check if fill function that fills StatisticsDto list from DailyVot list")
    void fillStatisticsDtoTest() {
        List<DailyVote> dvList = new ArrayList<>();
        List<StatisticsDto> statsDto = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            dvList.add(new DailyVote());
            dvList.get(i).setId((long) i);
            dvList.get(i).setComment("Comment: " + i);
        }

        voteApi.fillStatisticsDto(dvList, statsDto);
        for (int i = 0; i < 5; i++) {
            assertThat(statsDto.get(i).getComment(), equalTo(dvList.get(i).getComment()));
        }
    }

    @Test
    @DisplayName("checkSubmit: check for status METHOD NOT ALLOWED")
    void checkSubmitDailyVoteTest_METHOD_NOT_ALLOWED() {
        DailyVote day = new DailyVote();
        day.setUser(user);
        day.setDate(LocalDate.now());

        DailyVoteDto dailyVoteDto = new DailyVoteDto();
        dailyVoteDto.setMood(MoodStatus.HAPPY);

        when(userStore.getCurrentUser()).thenReturn(user);
        when(voteStore.getCurrentVoteDate(user, LocalDate.now())).thenReturn(Optional.of(day));
        when(wrapper.getMethodNotAllowedResponse()).thenReturn(response_METHOD_NOT_ALLOWED);

        Response resp = voteApi.checkSubmitDailyVote(dailyVoteDto);

        assertThat(resp, equalTo(response_METHOD_NOT_ALLOWED));
    }

    @Test
    @DisplayName("checkSubmit: check for status BAD_REQUEST (check if mood is chosen)")
    void checkSubmitDailyVoteTest_BAD_REQUEST() {
        DailyVoteDto dailyVoteDto = new DailyVoteDto();

        when(wrapper.getBadRequestResponse()).thenReturn(response_BAD_REQUEST);

        Response resp = voteApi.checkSubmitDailyVote(dailyVoteDto);

        assertThat(resp, equalTo(response_BAD_REQUEST));
    }

    @Test
    @DisplayName("checkSubmit: check for status CREATED")
    void checkSubmitDailyVoteTest_CREATED() {
        DailyVoteDto dailyVoteDto = new DailyVoteDto();
        dailyVoteDto.setMood(MoodStatus.HAPPY);

        when(wrapper.getCreatedResponse()).thenReturn(response_CREATED);

        Response resp = voteApi.checkSubmitDailyVote(dailyVoteDto);

        assertThat(resp, equalTo(response_CREATED));
    }
}
