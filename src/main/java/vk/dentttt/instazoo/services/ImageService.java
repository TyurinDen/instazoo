package vk.dentttt.instazoo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vk.dentttt.instazoo.entities.Image;
import vk.dentttt.instazoo.entities.Post;
import vk.dentttt.instazoo.entities.User;
import vk.dentttt.instazoo.exceptions.PostNotFoundException;
import vk.dentttt.instazoo.repositories.ImageRepository;
import vk.dentttt.instazoo.repositories.PostRepository;
import vk.dentttt.instazoo.repositories.UserRepository;

import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public Image uploadProfileImage(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByPrincipal(principal);
        log.info("Uploading profile image for user [{}]", user.getUsername());
        imageRepository.findByUserId(user.getId()).ifPresent(imageRepository::delete);

        Image image = Image.imageBuilder()
                           .title(file.getOriginalFilename())
                           .imageBytes(file.getBytes())
                           .build();

        return imageRepository.save(image);
    }

    public Image uploadPostImage(MultipartFile file, Long postId, Principal principal) throws IOException {
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findByIdAndUser(postId, user).orElseThrow(() -> {
            log.debug("Post with id [{}] for user [{}] not found", postId, user.getUsername());
            return new PostNotFoundException("Post with id [" + postId + "] for user [" + user.getUsername() + "] not found");
        });

        Image image = Image.imageBuilder()
                           .title(file.getOriginalFilename())
                           .postId(post.getId())
                           .userId(user.getId())
                           .imageBytes(file.getBytes())
                           .build();

        log.info("Image [{}] uploaded for post [{}]", image.getId(), post.getId());

        return imageRepository.save(image);
    }

    public Image getProfileImage(Principal principal) {
        User user = getUserByPrincipal(principal);
        return imageRepository.findByUserId(user.getId()).orElse(null); // TODO надо возвращать дефолтное изображание
    }

    public Image getPostImage(Long postId) {
        return imageRepository.findByPostId(postId).orElse(null); // TODO надо возвращать дефолтное изображание
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
