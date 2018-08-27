package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.*;
import lv.ctco.javaschool.vote.control.VoteStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    private Event event;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        user1 = new User();
        user1.setUsername("user1");

        user = new User();
        user.setUsername("user");
    }

    @Test
    @DisplayName("Check data for user1,Mood happy = 1,without comments, Date is today")
    void submitDailyVote_Happy_without_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyFeedbackDto dailyFeedbackDto = new DailyFeedbackDto();
        dailyFeedbackDto.setMood(1);
        dailyFeedbackDto.setComment("");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertEquals(user1, dailyVote.getUser());
            assertEquals(LocalDate.now().toString(), dailyVote.getDate());
            assertEquals(1, dailyFeedbackDto.getMood());
            assertEquals("", dailyFeedbackDto.getComment());

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyFeedbackDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    @DisplayName("Check data for user1 and Mood neutral = 2 and with comments")
    void submitDailyVote_Neutral_with_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyFeedbackDto dailyFeedbackDto = new DailyFeedbackDto();
        dailyFeedbackDto.setMood(2);
        dailyFeedbackDto.setComment("Test Comment");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertEquals(user1, dailyVote.getUser());
            assertEquals(LocalDate.now().toString(), dailyVote.getDate());
            assertEquals(2, dailyFeedbackDto.getMood());
            assertEquals("Test Comment", dailyFeedbackDto.getComment());

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyFeedbackDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    @DisplayName("Check data for user1 and Mood unhappy = 3 and with comments")
    void submitDailyVote_Unhappy_with_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        DailyFeedbackDto dailyFeedbackDto = new DailyFeedbackDto();
        dailyFeedbackDto.setMood(3);
        dailyFeedbackDto.setComment("Test Comment");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertEquals(user1, dailyVote.getUser());
            assertEquals(LocalDate.now().toString(), dailyVote.getDate());
            assertEquals(3, dailyFeedbackDto.getMood());
            assertEquals("Test Comment", dailyFeedbackDto.getComment());

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitDailyVote(dailyFeedbackDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }

    @Test
    void submitEventVote_Happy_without_comment() {
        event = new Event();
        event.setEventName("Event");
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(voteStore.findEventById(1))
                .thenReturn(event);

        EventFeedbackDto eventFeedbackDto = new EventFeedbackDto();
        eventFeedbackDto.setEventId(1);
        eventFeedbackDto.setMood(1);
        eventFeedbackDto.setComment("");

        doAnswer(invocation -> {
            EventVote eventVote = invocation.getArgument(0);
            assertEquals(user1, eventVote.getUser());
            assertEquals(event, eventVote.getEvent());
            assertEquals(1,eventVote.getMood());
            assertEquals("",eventVote.getComment());
            assertEquals(LocalDate.now().toString(), eventVote.getDate());
            assertEquals(VoteStatus.COMPLETE,eventVote.getStatus());
            return null;
        }).when(em).persist(any(EventVote.class));

        voteApi.submitEventVote(eventFeedbackDto);

        verify(em,times(1)).persist(any(EventVote.class));
    }

    @Test
    @DisplayName("Check vote status after select first event")
    void selectFirstVote() {
        EventType eventType = EventType.EVENT;

        when(userStore.getCurrentUser())
                .thenReturn(user);
        when(voteStore.getIncompleteVote(user))
                .thenReturn(Optional.empty());
        doAnswer(invocation -> {
            Vote vote = invocation.getArgument(0);
            assertEquals(user, vote.getUser());
            assertEquals(VoteStatus.INCOMPLETE, vote.getVoteStatus());
            assertEquals(eventType, vote.getEventType());
            return null;
        }).when(em).persist(any(Vote.class));

        voteApi.startVote(eventType);
        verify(em, times(1)).persist(any(Vote.class));
    }

    @Test
    @DisplayName("Check vote status after select second event")
    void selectSecondVote() {
        Vote vote = new Vote();
        vote.setVoteStatus(VoteStatus.INCOMPLETE);
        vote.setUser(user);
        vote.setEventType(EventType.EVENT);

        when(userStore.getCurrentUser())
                .thenReturn(user);
        when(voteStore.getIncompleteVote(user))
                .thenReturn(Optional.of(vote));
        voteApi.startVote(EventType.DAY);

        assertEquals(EventType.DAY, vote.getEventType());
    }

    @Test
    @DisplayName("Check vote status after redirect Mood page")
    void voteStatusIncompleteEvent() {
        EventType eventType = EventType.EVENT;

        Vote vote = new Vote();
        vote.setVoteStatus(VoteStatus.INCOMPLETE);
        vote.setUser(user);
        vote.setEventType(eventType);

        when(userStore.getCurrentUser())
                .thenReturn(user);
        when(voteStore.getIncompleteVote(user))
                .thenReturn(Optional.of(vote));

        VoteDto statusDto = voteApi.getStatus();

        assertEquals(VoteStatus.INCOMPLETE, statusDto.getStatus());
        assertTrue(statusDto.isEventStatus());
        assertFalse(statusDto.isDayStatus());
    }

    @Test
    @DisplayName("Check vote status after redirect Mood page")
    void voteStatusEmptyEvent() {
        EventType eventType = EventType.EVENT;

        Vote vote = new Vote();
        vote.setVoteStatus(VoteStatus.INCOMPLETE);
        vote.setUser(user);
        vote.setEventType(eventType);

        when(userStore.getCurrentUser())
                .thenReturn(user);
        when(voteStore.getIncompleteVote(user))
                .thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> voteApi.getStatus());
    }
}
