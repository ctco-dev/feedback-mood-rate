package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.FeedbackDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteApiTest {
    @Mock
    EntityManager em;
    @Mock
    UserStore userStore;
    @InjectMocks
    VoteApi voteApi;

    private User user1;

    @BeforeEach
    void init() {
        user1 = new User();
        user1.setUsername("user1");

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
