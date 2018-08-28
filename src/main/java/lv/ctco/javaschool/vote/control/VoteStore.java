package lv.ctco.javaschool.vote.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VoteStore {
    @PersistenceContext
    private EntityManager em;

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
