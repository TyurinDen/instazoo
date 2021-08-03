package vk.dentttt.instazoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundExcepttion extends RuntimeException {
    public CommentNotFoundExcepttion(String message) {
        super(message);
    }
}
