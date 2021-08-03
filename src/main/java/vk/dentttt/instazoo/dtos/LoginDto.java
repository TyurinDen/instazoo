package vk.dentttt.instazoo.dtos;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Value
public class LoginDto {

    @NotBlank(message = "Username cannot be blank or empty")
    String username;

    @NotBlank(message = "Password cannot be blank or empty")
    @Length(min = 6, message = "Password must be at least 6 chars long")
    String password;

}
