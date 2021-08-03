package vk.dentttt.instazoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends Exception {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
