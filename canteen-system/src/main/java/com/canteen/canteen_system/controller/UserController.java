package com.canteen.canteen_system.controller;

import com.canteen.canteen_system.dto.ChangePasswordRequest;
import com.canteen.canteen_system.dto.UserDto;
import com.canteen.canteen_system.mapper.UserMapper;
import com.canteen.canteen_system.model.User;
import com.canteen.canteen_system.repository.UserRepository;
import com.canteen.canteen_system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserRepository userRepository;

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
        User user = userService.getUserByName(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.userToUserDto(user));
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

    // not working
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        var user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            userMapper.update(userDto, user);
            userRepository.save(user);
            return ResponseEntity.ok(userMapper.userToUserDto(user));
        }
    }

    @PostMapping("/users/{id}/change-password")
    public ResponseEntity<Void> changepassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        var user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (!user.getPassword().equals(request.getOldPassword())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                user.setPassword(request.getNewPassword());
                userRepository.save(user);
                return ResponseEntity.noContent().build();
            }
        }
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<Void> deleteUser(@RequestBody User deletedUser) {
        var user = userService.getUserById(deletedUser.getId());
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (user.getEmail().equals(deletedUser.getEmail())
                    && deletedUser.getPassword().equals(user.getPassword())) {
                userRepository.delete(user);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
    }

}
