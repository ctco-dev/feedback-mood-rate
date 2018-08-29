package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;
import lv.ctco.javaschool.vote.entity.dto.DailyVoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
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
}
