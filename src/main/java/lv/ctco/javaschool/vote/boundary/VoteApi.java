package lv.ctco.javaschool.vote.boundary;

import jdk.nashorn.internal.runtime.logging.Logger;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.vote.control.VoteStore;
import lv.ctco.javaschool.vote.entity.EventType;
import lv.ctco.javaschool.vote.entity.Vote;
import lv.ctco.javaschool.vote.entity.VoteDto;
import lv.ctco.javaschool.vote.entity.VoteStatus;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/vote")
@Stateless
@Logger
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

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/submit")
    public void submitVote(JsonObject field) {
        User currentUser = userStore.getCurrentUser();
        Optional<Vote> vote = voteStore.getStartedVoteFor(currentUser, VoteStatus.INCOMPLETE, LocalDate.now());
      //  vote.ifPresent((Vote v) -> {

            List<String> vote_attributes = new ArrayList<>();
            for (Map.Entry<String, JsonValue> pair : field.entrySet()) {
               //log.info(pair.getKey() + " - " + pair.getValue());
                String addr = pair.getKey();
                System.out.println(addr);
                String value = ((JsonString) pair.getValue()).getString();
                System.out.println(value);
                // if ("SHIP".equals(value)) {
                vote_attributes.add(addr);
            }

           // v.setVoteStatus(VoteStatus.COMPLETE);


//        });
    }
    
}

