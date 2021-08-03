package vk.dentttt.instazoo.converters;

import org.springframework.stereotype.Component;
import vk.dentttt.instazoo.dtos.UserDto;
import vk.dentttt.instazoo.entities.User;

@Component
public class UserToUserDtoConverter {
    public UserDto convertUserToUserDto(User user) {
        return new UserDto(user.getFirstName(), user.getLastName(), user.getBio());
    }
}
