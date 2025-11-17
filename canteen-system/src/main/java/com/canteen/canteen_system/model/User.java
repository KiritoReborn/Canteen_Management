package com.canteen.canteen_system.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum Role {
    STUDENT,
    STAFF
}

@Getter // Lombok: Creates all getter methods
@Setter // Lombok: Creates all setter methods
@NoArgsConstructor // Lombok: Creates the required public User() {} constructor
@AllArgsConstructor 
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role; // Use the Role enum you just created
    private LocalDateTime createdAt;
}