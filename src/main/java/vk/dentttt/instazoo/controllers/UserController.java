package vk.dentttt.instazoo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.dentttt.instazoo.converters.UserToUserDtoConverter;
import vk.dentttt.instazoo.dtos.UserDto;
import vk.dentttt.instazoo.entities.User;
import vk.dentttt.instazoo.services.IncorrectRequestService;
import vk.dentttt.instazoo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserToUserDtoConverter userDtoConverter;
    private final IncorrectRequestService incorrectRequestService;

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        return ResponseEntity.ok(userDtoConverter.convertUserToUserDto(user));
    }

    @PostMapping("/{userId}") //TODO то любой пользователь может получить информацию о другом пользователе???
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userDtoConverter.convertUserToUserDto(user));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(
            @Valid @RequestBody UserDto userDto,
            Principal principal,
            BindingResult bindingResult)
    {
        ResponseEntity<?> errors = incorrectRequestService.getValidationErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        User user = userService.updateUser(userDto, principal);
        UserDto updatedUser = userDtoConverter.convertUserToUserDto(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
