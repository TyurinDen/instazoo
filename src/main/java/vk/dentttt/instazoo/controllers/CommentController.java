package vk.dentttt.instazoo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.dentttt.instazoo.converters.CommentToCommentDtoConverter;
import vk.dentttt.instazoo.converters.PostToPostDtoConverter;
import vk.dentttt.instazoo.dtos.CommentDto;
import vk.dentttt.instazoo.dtos.ResponseObject;
import vk.dentttt.instazoo.entities.Comment;
import vk.dentttt.instazoo.services.CommentService;
import vk.dentttt.instazoo.services.IncorrectRequestService;
import vk.dentttt.instazoo.services.PostService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentToCommentDtoConverter commentDtoConverter;
    private final IncorrectRequestService incorrectRequestService;

    @PostMapping("/{postId}/create")
    public ResponseEntity<?> createComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable("postId") Long postId,
            Principal principal,
            BindingResult bindingResult
    ) {
        ResponseEntity<?> errors = incorrectRequestService.getValidationErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Comment comment = commentService.saveComment(postId, commentDto, principal);
        return new ResponseEntity<>(commentDtoConverter.convertCommentToCommentDto(comment), HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable("postId") Long postId) {
        List<CommentDto> commentDtos = commentService.getAllCommentsForPost(postId)
                .stream()
                .map(commentDtoConverter::convertCommentToCommentDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentDtos);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ResponseObject("Comment with ID:" + commentId + " deleted"));
    }

}
