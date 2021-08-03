package vk.dentttt.instazoo.converters;

import org.springframework.stereotype.Component;
import vk.dentttt.instazoo.dtos.PostDto;
import vk.dentttt.instazoo.entities.Post;

@Component
public class PostToPostDtoConverter {
    public PostDto convertPostToPostDto(Post post) {
        return new PostDto(post.getUser().getUsername(), post.getTitle(), post.getContent(), post.getLocation(),
                post.getLikes(), post.getLikedUsers()
        );
    }
}
