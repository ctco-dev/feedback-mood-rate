package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VoteApiTest {
    @Mock
    EntityManager em;
    @Mock
    UserStore userStore;
    @Mock
    VoteStore voteStore;
    @InjectMocks
    VoteApi voteApi;

    private User user;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setUsername("user");
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