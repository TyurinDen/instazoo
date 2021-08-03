package vk.dentttt.instazoo.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vk.dentttt.instazoo.entities.Role;
import vk.dentttt.instazoo.entities.Status;
import vk.dentttt.instazoo.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUserFactory {

    public static JwtUser createJwtUser(User user) {
        return JwtUser.builder()
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .username(user.getUsername())
                      .password(user.getPassword())
                      .email(user.getEmail())
                      .enabled(user.getStatus().equals(Status.ACTIVE))
                      .authorities(mapToGrantedAuthorities(user.getRoles()))
                      .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());
    }

}
