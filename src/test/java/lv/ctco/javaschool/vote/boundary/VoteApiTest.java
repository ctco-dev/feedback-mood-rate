package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import lv.ctco.javaschool.vote.entity.dto.EventDto;
import lv.ctco.javaschool.vote.entity.dto.EventVoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VoteApiTest {
    @Mock
    private EntityManager em;
    @Mock
    private UserStore userStore;
    @Mock
    private VoteStore voteStore;

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
    void getEventsTest() {
        Event newEvent = new Event();
        newEvent.setEventName("test");
        newEvent.setId((long) 0);
        newEvent.setDate(LocalDate.of(2018, 8, 30));
        newEvent.setVoteDeadlineDate(LocalDate.now().plusDays(5));

        EventVote eventVote = new EventVote();
        eventVote.setEvent(newEvent);
        eventVote.setId((long)1);
        eventVote.setUser(user1);
        eventVote.setMood(MoodStatus.EMPTY);

        List<EventVote> eventVoteList = new ArrayList<>();
        eventVoteList.add(eventVote);

        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(voteStore.getEventVoteByUserId(user1))
                .thenReturn(eventVoteList);

        List<EventDto> actual = voteApi.getEventsByCurrentUser();
        assertThat(actual.get(0).getEventName(), equalTo("test"));
    }

    @Test
    @DisplayName("allEvent: check list of active events")
    void getAllEvents() {
        Event event = new Event();
        event.setEventName("New Year 2018");

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        List<EventDto> eventDtoList;

        when(voteStore.getAllEvents()).thenReturn(eventList);

        eventDtoList = voteApi.getAllEvents();

        assertThat(eventDtoList.get(0).getEventName(), equalTo(eventList.get(0).getEventName()));
    }

    @Test
    @DisplayName("submitDailyVoteTest: Check data for user1,MoodStatus.EMPTY,without comments, Date is today")
    void submitDailyVote_EMPTY_without_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyVoteDto dailyVoteDto = new DailyVoteDto();
        dailyVoteDto.setMood(MoodStatus.EMPTY);
        dailyVoteDto.setComment("");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertThat(dailyVote.getUser(),equalTo(user1));
            assertThat(dailyVote.getDate(),equalTo(LocalDate.now()));
            assertThat(dailyVoteDto.getMood(),equalTo(MoodStatus.EMPTY));
            assertThat(dailyVoteDto.getComment(),equalTo(""));

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyVoteDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    @DisplayName("submitDailyVoteTest: Check data for user1,MoodStatus.HAPPY,without comments, Date is today")
    void submitDailyVote_Happy_without_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyVoteDto dailyVoteDto = new DailyVoteDto();
        dailyVoteDto.setMood(MoodStatus.HAPPY);
        dailyVoteDto.setComment("");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);

            assertThat(dailyVote.getUser(),equalTo(user1));
            assertThat(dailyVote.getDate(),equalTo(LocalDate.now()));
            assertThat(dailyVoteDto.getMood(),equalTo(MoodStatus.HAPPY));
            assertThat(dailyVoteDto.getComment(),equalTo(""));

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyVoteDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    @DisplayName("submitDailyVoteTest: Check data for user1 and MoodStatus.NEUTRAL and with comments")
    void submitDailyVote_Neutral_with_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyVoteDto dailyVoteDto = new DailyVoteDto();
        dailyVoteDto.setMood(MoodStatus.NEUTRAL);
        dailyVoteDto.setComment("Test Comment");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);

            assertThat(dailyVote.getUser(),equalTo(user1));
            assertThat(dailyVote.getDate(),equalTo(LocalDate.now()));
            assertThat(dailyVoteDto.getMood(),equalTo(MoodStatus.NEUTRAL));
            assertThat(dailyVoteDto.getComment(),equalTo("Test Comment"));

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyVoteDto);
        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    @DisplayName("submitDailyVoteTest: Check data for user1 and MoodStatus.SAD and with comments")
    void submitDailyVote_Unhappy_with_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyVoteDto dailyVoteDto = new DailyVoteDto();
        dailyVoteDto.setMood(MoodStatus.SAD);
        dailyVoteDto.setComment("Test Comment");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);

            assertThat(dailyVote.getUser(),equalTo(user1));
            assertThat(dailyVote.getDate(),equalTo(LocalDate.now()));
            assertThat(dailyVoteDto.getMood(),equalTo(MoodStatus.SAD));
            assertThat(dailyVoteDto.getComment(),equalTo("Test Comment"));

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyVoteDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    @DisplayName("GetAllEventVotesTest: Checks if method returns correct dto value")
    void getAllEventVotesTest(){
        List<EventVote> eventVoteList= new ArrayList<>();
        EventVote ev = new EventVote();
        Event event = new Event();

        event.setEventName("testEvent");
        ev.setComment("test");
        ev.setMood(MoodStatus.HAPPY);
        ev.setUser(user1);
        ev.setEvent(event);
        eventVoteList.add(ev);

        when(voteStore.getAllEventVotes())
                .thenReturn(eventVoteList);

        List<EventVoteDto> test = voteApi.getAllEventVotes();
        assertThat(test.get(0).getComment(), equalTo(ev.getComment()));
        assertThat(test.get(0).getEventName(), equalTo(event.getEventName()));
        assertThat(test.get(0).getMood(), equalTo(ev.getMood()));
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
}
