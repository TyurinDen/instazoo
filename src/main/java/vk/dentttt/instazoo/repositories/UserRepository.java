package vk.dentttt.instazoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.dentttt.instazoo.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> getUserById(Long id);

}
