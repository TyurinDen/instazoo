package vk.dentttt.instazoo.converters;

import org.springframework.stereotype.Component;
import vk.dentttt.instazoo.dtos.CommentDto;
import vk.dentttt.instazoo.entities.Comment;

@Component
public class CommentToCommentDtoConverter {
    public CommentDto convertCommentToCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getContent(), comment.getUsername());
    }
}
