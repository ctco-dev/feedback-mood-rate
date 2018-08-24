package lv.ctco.javaschool.auth.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.Role;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserDto;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Optional;

@Path("/user")
public class UserResourceApi {
    @Inject
    private UserStore userStore;

    @GET
    @Path("/list")
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public List<User> getUserList() {
        return userStore.getAllUsers();
    }

    @GET
    @Path("/role")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public UserDto getUserRole() {
        User currentUser = userStore.getCurrentUser();
        Optional<Role> userRole = userStore.getRoleByUsername(currentUser.getUsername());

        return userRole.map(u -> {
            UserDto dto = new UserDto();
            dto.setUsername(currentUser.getUsername());
            dto.setRole(u);
            return dto;
        }).orElseThrow(IllegalStateException::new);
    }
}
