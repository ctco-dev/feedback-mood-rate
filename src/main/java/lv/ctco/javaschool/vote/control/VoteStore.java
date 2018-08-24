package lv.ctco.javaschool.vote.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventType;
import lv.ctco.javaschool.vote.entity.Vote;
import lv.ctco.javaschool.vote.entity.VoteStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
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

    public List<Event> getIncompleteEventList(User user) {
        return em.createQuery(
                "select e " +
                        "from Event e " +
                        "where :user member of e.user", Event.class)
                .setParameter("user", user)
                .getResultList();
    }
}
