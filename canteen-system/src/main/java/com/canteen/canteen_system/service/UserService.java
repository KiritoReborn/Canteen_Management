package com.canteen.canteen_system.service;

import com.canteen.canteen_system.dto.LoginDto;
import com.canteen.canteen_system.model.User;
import com.canteen.canteen_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already in use");
        }
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User loginUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRole(String role) {
        com.canteen.canteen_system.model.Role roleEnum = com.canteen.canteen_system.model.Role
                .valueOf(role);
        // Return all users with this role (fix: was returning single user)
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == roleEnum)
                .toList();
    }

    public User getUserByName(String username) {
        return userRepository.findByName(username);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
