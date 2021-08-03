package vk.dentttt.instazoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.dentttt.instazoo.entities.Post;
import vk.dentttt.instazoo.entities.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserOrderByCreatedDesc(User user);

    List<Post> findAllByOrderByCreatedDesc();

    Optional<Post> findByIdAndUser(Long id, User user);

}
