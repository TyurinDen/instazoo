package vk.dentttt.instazoo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vk.dentttt.instazoo.dtos.CommentDto;
import vk.dentttt.instazoo.entities.Comment;
import vk.dentttt.instazoo.entities.Post;
import vk.dentttt.instazoo.entities.User;
import vk.dentttt.instazoo.exceptions.CommentNotFoundExcepttion;
import vk.dentttt.instazoo.exceptions.PostNotFoundException;
import vk.dentttt.instazoo.repositories.CommentRepository;
import vk.dentttt.instazoo.repositories.PostRepository;
import vk.dentttt.instazoo.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Comment saveComment(Long postId, CommentDto commentDto, Principal principal) {
        User user = getUserByPrincipal(principal);

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.debug("Post with id [{}] not found", postId);
            return new PostNotFoundException("Post with id [" + postId + "] not found");
        });

        Comment comment = Comment.builder()
                                 .content(commentDto.getContent())
                                 .username(user.getUsername())
                                 .post(post)
                                 .build();

        log.info("Saved comment with id [{}] for post with id [{}]", comment.getId(), post.getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.debug("Post with id [{}] not found", postId);
            return new PostNotFoundException("Post with id [" + postId + "] not found");
        });

        return commentRepository.findCommentsByPostOOrderByCreatedDesc(post);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            log.debug("Comment with id [{}] not found", commentId);
            return new CommentNotFoundExcepttion("Comment with id [" + commentId + "] not found");
        });

        commentRepository.delete(comment);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("User with username [{}] not found", username);
                    return new UsernameNotFoundException("User with username [" + username + "] not found");
                });
    }

}
