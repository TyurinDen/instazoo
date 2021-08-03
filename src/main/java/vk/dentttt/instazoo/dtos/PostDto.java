package vk.dentttt.instazoo.dtos;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Value
public class PostDto {

    @NotBlank(message = "Username cannot be blank or empty")
    String username;

    @NotBlank(message = "Title cannot be blank or empty")
    String title;

    @NotBlank(message = "Content cannot be blank or empty")
    String content;

    String location;

    int likes;

    Set<String> likedUsers;

}
