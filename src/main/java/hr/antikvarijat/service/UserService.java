package hr.antikvarijat.service;

import hr.antikvarijat.dto.UserDto;
import hr.antikvarijat.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
