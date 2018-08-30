package lv.ctco.javaschool.vote.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VoteStore {
    @PersistenceContext
    private EntityManager em;

    public List<EventVote> getEventVoteByUserId(User user) {
        return em.createQuery(
                "select ev " +
                        "from EventVote ev " +
                        "where ev.user = :user and ev.mood = :moodStatus", EventVote.class)
                .setParameter("user", user)
                .setParameter("moodStatus", MoodStatus.EMPTY)
                .getResultList();
    }

    public List<EventVote> getAllEventVotes(User user) {
        return em.createQuery(
                "select ev " +
                        "from EventVote ev " +
                        "where ev.user = :user", EventVote.class)
                .setParameter("user", user)
                .getResultList();
    }

    public EventVote getEventVoteByUserIdEventId(User user, Event event) {
        return em.createQuery(
                "select ev " +
                        "from EventVote ev " +
                        "where ev.user = :user and ev.event = :event and ev.mood = :moodStatus", EventVote.class)
                .setParameter("user", user)
                .setParameter("event", event)
                .setParameter("moodStatus", MoodStatus.EMPTY)
                .getResultStream()
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public Event getEventByEventName(String eventName) {
        return em.createQuery(
                "select e " +
                        "from Event e " +
                        "where e.eventName = :eventName", Event.class)
                .setParameter("eventName", eventName)
                .getResultStream()
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
