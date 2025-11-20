package com.canteen.canteen_system.controller;

import com.canteen.canteen_system.dto.UserDto;
import com.canteen.canteen_system.mapper.UserMapper;
import com.canteen.canteen_system.model.User;
import com.canteen.canteen_system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users/id/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userMapper.userToUserDto(user));
        }
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sort) {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/name/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        return ResponseEntity.notFound().build(); // Placeholder until Service is updated
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<UserDto> getUserByRole(@PathVariable String role) {
        User user = userService.getUserByRole(role);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userMapper.userToUserDto(user));
        }
    }
}
