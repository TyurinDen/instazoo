package vk.dentttt.instazoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.dentttt.instazoo.entities.Comment;
import vk.dentttt.instazoo.entities.Post;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByUsernameOrderByCreatedDesc(String username);

    List<Comment> findCommentsByPostOOrderByCreatedDesc(Post post);

}
