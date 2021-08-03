package vk.dentttt.instazoo.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class IncorrectRequestService {

    public ResponseEntity<Object> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors;

        if (bindingResult.hasErrors()) {
            errors = new HashMap<>();
            
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getCode(), objectError.getDefaultMessage());
            }

            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                errors.put(fieldError.getCode(), fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

}
