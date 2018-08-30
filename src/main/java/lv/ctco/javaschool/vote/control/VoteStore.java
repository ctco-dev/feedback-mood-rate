package lv.ctco.javaschool.vote.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.DailyVote;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.MoodStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Stateless
public class VoteStore {
    @PersistenceContext
    private EntityManager em;

    public Optional<DailyVote> getCurrentVoteDate(User user, LocalDate date){
        return em.createQuery("select  d " +
                "from DailyVote d " +
                "where d.user = :user and d.date = :date", DailyVote.class)
                .setParameter("user", user)
                .setParameter("date", date)
                .getResultStream()
                .findFirst();
    }

    public List<Event> getIncompleteEventList(User user) {
        return em.createQuery(
                "select e " +
                        "from Event e " +
                        "where :user member of e.user", Event.class)
                .setParameter("user", user)
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
