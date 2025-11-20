package com.canteen.canteen_system.mapper;

import com.canteen.canteen_system.dto.LoginDto;
import com.canteen.canteen_system.dto.UserDto;
import com.canteen.canteen_system.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    LoginDto loginToLoginDto(User user);
}

