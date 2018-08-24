package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.FeedbackDto;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.EventType;
import lv.ctco.javaschool.vote.entity.Vote;
import lv.ctco.javaschool.vote.entity.VoteDto;
import lv.ctco.javaschool.vote.entity.VoteStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VoteApiTest {
    @Mock
    EntityManager em;
    @Mock
    UserStore userStore;

    @InjectMocks
    VoteApi voteApi;

    private User user1;

    @Mock
    VoteStore voteStore;
    
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
    @DisplayName("Check data for user1,Mood happy = 1,without comments, Data is today")
    void submitVote_Happy_without_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setMood(1);
        feedbackDto.setComment("");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertEquals(user1, dailyVote.getUser());
            assertEquals(LocalDate.now().toString(), dailyVote.getDate());
            assertEquals(1, feedbackDto.getMood());
            assertEquals("", feedbackDto.getComment());

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitVote(feedbackDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }
@Test
@DisplayName("Check data for user1 and Mood neutral = 2 and with comments")
        void submitVote_Neutral_with_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setMood(2);
        feedbackDto.setComment("Test Comment");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertEquals(user1, dailyVote.getUser());
            assertEquals(LocalDate.now().toString(), dailyVote.getDate());
            assertEquals(2, feedbackDto.getMood());
            assertEquals("Test Comment", feedbackDto.getComment());

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitVote(feedbackDto);

        verify(em, times(1)).persist(any(DailyVote.class));
        }
    @Test
    @DisplayName("Check data for user1 and Mood unhappy = 3 and with comments")
    void submitVote_Unhappy_with_Comment() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setMood(3);
        feedbackDto.setComment("Test Comment");
        doAnswer(invocation -> {
            DailyVote dailyVote = invocation.getArgument(0);
            assertEquals(user1, dailyVote.getUser());
            assertEquals(LocalDate.now().toString(), dailyVote.getDate());
            assertEquals(3, feedbackDto.getMood());
            assertEquals("Test Comment", feedbackDto.getComment());

            return null;
        }).when(em).persist(any(DailyVote.class));

        voteApi.submitVote(feedbackDto);

        verify(em, times(1)).persist(any(DailyVote.class));
    }
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

        assertEquals(VoteStatus.INCOMPLETE ,statusDto.getStatus());
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
