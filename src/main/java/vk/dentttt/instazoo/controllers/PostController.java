package vk.dentttt.instazoo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.dentttt.instazoo.converters.PostToPostDtoConverter;
import vk.dentttt.instazoo.dtos.PostDto;
import vk.dentttt.instazoo.entities.Post;
import vk.dentttt.instazoo.services.IncorrectRequestService;
import vk.dentttt.instazoo.services.PostService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostToPostDtoConverter postDtoConverter;
    private final IncorrectRequestService incorrectRequestService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, Principal principal, BindingResult bindingResult) {
        ResponseEntity<?> errors = incorrectRequestService.getValidationErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Post post = postService.createPost(postDto, principal);
        return new ResponseEntity<>(postDtoConverter.convertPostToPostDto(post), HttpStatus.OK);
    }
}
