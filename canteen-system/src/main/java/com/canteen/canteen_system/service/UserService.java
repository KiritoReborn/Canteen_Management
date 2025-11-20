package com.canteen.canteen_system.service;

import com.canteen.canteen_system.dto.LoginDto;
import com.canteen.canteen_system.model.User;
import com.canteen.canteen_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class UserService {

    private final UserRepository userRepository;
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already in use");
        }
        return userRepository.save(user);
    }

    public User loginUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user!=null&&user.getPassword().equals(loginDto.getPassword())) {
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

    public User getUserByRole(String role) {
        com.canteen.canteen_system.model.Role roleEnum = com.canteen.canteen_system.model.Role.valueOf(role.toUpperCase());
        return userRepository.findByRole(roleEnum);
    }
}
