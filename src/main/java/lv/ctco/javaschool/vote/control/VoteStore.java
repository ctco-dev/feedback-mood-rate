package lv.ctco.javaschool.vote.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;
import lv.ctco.javaschool.vote.entity.Vote;
import lv.ctco.javaschool.vote.entity.VoteStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class VoteStore {
    @PersistenceContext
    private EntityManager em;

    public Optional<Vote> getIncompleteVote(User user) {
        return em.createQuery(
                "select v " +
                        "from Vote v " +
                        "where v.user = :user and v.voteStatus = :voteStatus", Vote.class)
                .setParameter("user", user)
                .setParameter("voteStatus", VoteStatus.INCOMPLETE)
                .getResultStream()
                .findFirst();
    }

    public List<EventVote> getEventVoteByUserId(User user) {
        return em.createQuery(
                "select ev " +
                        "from EventVote ev " +
                        "where ev.user = :user and ev.mood = :moodStatus", EventVote.class)
                .setParameter("user", user)
                .setParameter("moodStatus", MoodStatus.EMPTY)
                .getResultList();
    }

    public Event findEventById(int eventId) {
        return em.createQuery("select e from Event e where e.id=:id",Event.class)
                .setParameter("id",eventId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
