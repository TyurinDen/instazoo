package vk.dentttt.instazoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserSavingException extends RuntimeException {

    public UserSavingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSavingException(String message) {
        super(message);
    }

}
