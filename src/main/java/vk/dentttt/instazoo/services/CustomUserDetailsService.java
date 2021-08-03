package vk.dentttt.instazoo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vk.dentttt.instazoo.entities.User;
import vk.dentttt.instazoo.repositories.UserRepository;
import vk.dentttt.instazoo.security.JwtUserFactory;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("User with username [" + username + "] not found");
                    throw new UsernameNotFoundException("User with username [" + username + "] not found");
                });

        log.info("IN loadUserByUsername - user with username: [{}] successfully loaded", username);
        return JwtUserFactory.createJwtUser(user);
    }

}
