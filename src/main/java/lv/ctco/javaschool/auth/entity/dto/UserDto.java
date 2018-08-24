package lv.ctco.javaschool.auth.entity.dto;

import lv.ctco.javaschool.auth.entity.domain.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserDto {
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
