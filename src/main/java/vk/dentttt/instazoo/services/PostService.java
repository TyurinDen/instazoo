package vk.dentttt.instazoo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vk.dentttt.instazoo.dtos.PostDto;
import vk.dentttt.instazoo.entities.Image;
import vk.dentttt.instazoo.entities.Post;
import vk.dentttt.instazoo.entities.User;
import vk.dentttt.instazoo.exceptions.PostNotFoundException;
import vk.dentttt.instazoo.repositories.ImageRepository;
import vk.dentttt.instazoo.repositories.PostRepository;
import vk.dentttt.instazoo.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public Post createPost(PostDto postDto, Principal principal) {
        String username = principal.getName();
        User user = userRepository //TODO вынести в отдельный метод!!!
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("User with username [{}] not found", username);
                    return new UsernameNotFoundException("User with username [" + username + "] not found");
                });

        Post post = Post.postBuilder()
                        .title(postDto.getTitle())
                        .content(postDto.getContent())
                        .location(postDto.getLocation())
                        .user(user)
                        .comments(null)
                        .likes(0)
                        .likedUsers(null)
                        .build();

        log.info("Saving post with title [{}] for user [{}]", postDto.getTitle(), username);
        return postRepository.save(post);
    }

    public List<Post> getPostsForUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("User with username [{}] not found", username);
                    return new UsernameNotFoundException("User with username [" + username + "] not found");
                });

        return postRepository.findByUserOrderByCreatedDesc(user);
    }

    public Post likePost(Long postId, Principal principal) { //TODO откуда мы возьмем ИД поста???
        String username = principal.getName();

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.debug("Post with id [{}] not found", postId);
            return new PostNotFoundException("Post with id [" + postId + "] not found");
        });

        if (post.getLikedUsers().contains(username)) {
            post.setLikes(post.getLikes() - 1);
        } else {
            post.setLikes(post.getLikes() + 1);
        }

        return postRepository.save(post);
    }

    public Post getPostByIdAndUser(Long postId, Principal principal) { //TODO откуда мы возьмем ИД поста???
        String username = principal.getName();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.debug("User with username [{}] not found", username);
                    return new UsernameNotFoundException("User with username [" + username + "] not found");
                });

        return postRepository.findByIdAndUser(postId, user).orElseThrow(() -> {
            log.debug("Post with id [{}] not found", postId);
            return new PostNotFoundException("Post with id [" + postId + "] not found");
        });

    }

    public void deletePost(Long postId, Principal principal) { //TODO откуда мы возьмем ИД прста???
        Post post = getPostByIdAndUser(postId, principal);
        Optional<Image> image = imageRepository.findByPostId(postId);
        image.ifPresent(imageRepository::delete);
        postRepository.delete(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDesc();
    }

}
