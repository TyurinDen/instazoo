package vk.dentttt.instazoo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vk.dentttt.instazoo.dtos.SignupDto;
import vk.dentttt.instazoo.dtos.UserDto;
import vk.dentttt.instazoo.entities.Role;
import vk.dentttt.instazoo.entities.Status;
import vk.dentttt.instazoo.entities.User;
import vk.dentttt.instazoo.exceptions.RoleNotFoundException;
import vk.dentttt.instazoo.exceptions.UserSavingException;
import vk.dentttt.instazoo.repositories.RoleRepository;
import vk.dentttt.instazoo.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @SuppressWarnings("UnusedReturnValue")
    public User createUser(SignupDto userIn) {
        User user = User.userBuilder()
                        .firstName(userIn.getFirstname())
                        .lastName(userIn.getLastname())
                        .username(userIn.getUsername())
                        .email(userIn.getEmail())
                        .password(passwordEncoder.encode(userIn.getPassword()))
                        .status(Status.ACTIVE)
                        .build();

        Role userRole = roleRepository.getByName("USER");
        user.setRoles(List.of(userRole));

        try {
            log.info("Saving user {}", userIn);
            return userRepository.save(user);
        } catch (Exception e) {
            log.debug("Error saving user {} with message: {}", userIn, e.getMessage());
            throw new UserSavingException(
                    "Error saving user with username: " + userIn.getUsername(), e);
        }
    }

    public User updateUser(UserDto userDto, Principal principal) {
        String username = principal.getName();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("User with username [{}] not found", username);
                    return new UsernameNotFoundException("User with username [" + username + "] not found");
                });

        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setBio(userDto.getBio());

        return userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("IN UserService:getUserByPrincipal User with username [{}] not found", username);
                    return new UsernameNotFoundException(
                            "IN UserService:getUserByPrincipal User with username [" + username + "] not found"
                    );
                });
    }

    public User getUserById(Long userId) {
        return userRepository.getById(userId);
    }

}
