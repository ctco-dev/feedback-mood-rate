package lv.ctco.javaschool.vote.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.boundary.VoteApi;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.Vote;
import lv.ctco.javaschool.vote.entity.VoteStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
                        "where ev.user = :user", EventVote.class)
                .setParameter("user", user)
                .getResultList();
    }

//    public List<Event> getEventList(User user) {
//        return em.createQuery(
//                "select e " +
//                        "from Event e " +
//                        "where :user member of e.user", Event.class)
//                .setParameter("user", user)
//                .getResultList();
//    }

    public List<Event> getEventVoteList(User user) {
        LocalDate localDate = getTodayDate();
        return em.createQuery(
                "select e " +
                        "from User user " +
                        "INNER JOIN EventVote ev ON ev.user = user " +
                        "INNER JOIN Event e ON ev.event = e " +
                        "where user = :user AND e.voteDeadlineDate > :localDate", Event.class)
                .setParameter("user", user)
                .setParameter("localDate", localDate)
                .getResultList();
    }

    public LocalDate getTodayDate () {
        LocalDate date = LocalDate.now();
        return date;
    }
}
