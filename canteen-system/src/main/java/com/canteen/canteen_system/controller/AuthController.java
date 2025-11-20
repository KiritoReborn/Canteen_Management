package com.canteen.canteen_system.controller;

import com.canteen.canteen_system.dto.LoginDto;
import com.canteen.canteen_system.model.User;
import com.canteen.canteen_system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, UriComponentsBuilder uribuilder) {
        User registeredUser = userService.registerUser(user);
        var uri = uribuilder.path("/users/id/{id}").buildAndExpand(registeredUser.getId()).toUri();
        return ResponseEntity.created(uri).body(uri.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        User user = userService.loginUser(loginDto);
        if (user != null) {
            return ResponseEntity.ok("Login Successful");
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
        }
    }
}
