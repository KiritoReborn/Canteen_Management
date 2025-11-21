package com.canteen.canteen_system.repository;

import com.canteen.canteen_system.model.User;
import com.canteen.canteen_system.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByName(String name);
    User findByRole(Role role);

}
