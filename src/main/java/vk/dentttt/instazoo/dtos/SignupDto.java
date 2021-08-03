package vk.dentttt.instazoo.dtos;

import lombok.Value;
import org.hibernate.validator.constraints.Length;
import vk.dentttt.instazoo.annotations.ValidatedPasswords;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@ValidatedPasswords
public class SignupDto {

    @NotBlank(message = "Firstname cannot be blank or empty")
    String firstname;

    @NotBlank(message = "Lastname cannot be blank or empty")
    String lastname;

    @NotBlank(message = "Username cannot be blank or empty")
    String username;

    @Email(message = "It must be in email format")
    String email;

    @NotBlank(message = "Password cannot be blank or empty")
    @Length(min = 6, message = "Password must be at least 6 chars long")
    String password;

    String confirmPassword;

}
