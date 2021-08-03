package vk.dentttt.instazoo.dtos;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class UserDto {

    @NotBlank(message = "Firstname cannot be blank or empty")
    String firstname;

    @NotBlank(message = "Lastname cannot be  blank or empty")
    String lastname;

    @NotBlank(message = "Bio cannot be blank or empty")
    String bio;

}
