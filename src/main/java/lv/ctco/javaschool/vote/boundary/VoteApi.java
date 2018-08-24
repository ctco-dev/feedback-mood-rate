package lv.ctco.javaschool.vote.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/vote")
@Stateless
public class VoteApi {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserStore userStore;
    @Inject
    private VoteStore voteStore;

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/start")
    public void startVote(EventType eventType) {
        User currentUser = userStore.getCurrentUser();
        Optional<Vote> vote = voteStore.getIncompleteVote(currentUser);

        vote.ifPresent(v -> {
            if (v.getEventType() != eventType) {
                v.setEventType(eventType);
            }
        });

        if (!vote.isPresent()) {
            Vote newVote = new Vote();
            newVote.setUser(currentUser);
            newVote.setVoteStatus(VoteStatus.INCOMPLETE);
            newVote.setEventType(eventType);
            em.persist(newVote);
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/status")
    public VoteDto getStatus() {
        User currentUser = userStore.getCurrentUser();
        Optional<Vote> vote = voteStore.getIncompleteVote(currentUser);

        return vote.map(v -> {
            VoteDto dto = new VoteDto();
            dto.setStatus(v.getVoteStatus());
            if (v.getEventType() == EventType.DAY) {
                dto.setDayStatus(true);
            } else {
                dto.setDayStatus(false);
            }
            if (v.getEventType() == EventType.EVENT) {
                dto.setEventStatus(true);
            } else {
                dto.setEventStatus(false);
            }
            return dto;
        }).orElseThrow(IllegalStateException::new);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/event")
    public EventDtoList getIncompleteEvents() {
        User currentUser = userStore.getCurrentUser();
        List<Event> eventList = voteStore.getIncompleteEventList(currentUser);
        List<EventDto> eventDtos = new ArrayList<>();

        eventList.forEach(ev -> {
            EventDto evDto = new EventDto();
            evDto.setUser(ev.getUser());
            evDto.setEventName(ev.getEventName());
            evDto.setDate(ev.getDate());
            eventDtos.add(evDto);
        });
        EventDtoList evList = new EventDtoList();
        evList.setEventDtoList(eventDtos);
        return evList;
    }
}
