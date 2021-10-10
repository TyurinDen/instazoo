package vk.dentttt.instazoo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.dentttt.instazoo.dtos.LoginDto;
import vk.dentttt.instazoo.dtos.ResponseObject;
import vk.dentttt.instazoo.dtos.SignupDto;
import vk.dentttt.instazoo.security.JwtProvider;
import vk.dentttt.instazoo.services.UserService;
import vk.dentttt.instazoo.services.IncorrectRequestService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    //TODO заменить ответы контролера на ErrorObject из кода и сообщения

    private final IncorrectRequestService requestValidator;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto request, BindingResult bindingResult) {
        if (requestValidator.getValidationErrors(bindingResult) != null) {
            return requestValidator.getValidationErrors(bindingResult);
        }

        userService.createUser(request);
        return ResponseEntity.ok(
                new ResponseObject("User with username [" + request.getUsername() + "] successfully registered"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto request, BindingResult bindingResult) {
        if (requestValidator.getValidationErrors(bindingResult) != null) {
            return requestValidator.getValidationErrors(bindingResult);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new ResponseObject(jwtProvider.generateToken(authentication)));
    }

}
