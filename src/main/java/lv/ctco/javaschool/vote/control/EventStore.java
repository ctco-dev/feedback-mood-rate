package lv.ctco.javaschool.vote.control;


import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.entity.Event;
import lv.ctco.javaschool.vote.entity.EventVote;
import lv.ctco.javaschool.vote.entity.MoodStatus;
import lv.ctco.javaschool.vote.entity.dto.EventDto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EventStore {
    @PersistenceContext
    private EntityManager em;

    public void saveNewEvent(EventDto eventDto){
        Event newEvent = new Event();
        newEvent.setEventName(eventDto.getEventName());
        newEvent.setDate(eventDto.getDate());
        newEvent.setVoteDeadlineDate(eventDto.getDeadlineDate());
        em.persist(newEvent);
    }

    public void createNewEventVote (Event lastEvent, User user){
        EventVote newEventVote = new EventVote();
        newEventVote.setEvent(lastEvent);
        newEventVote.setUser(user);
        newEventVote.setMood(MoodStatus.EMPTY);
        em.persist(newEventVote);
    }
}
