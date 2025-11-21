package com.canteen.canteen_system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.canteen.canteen_system.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;



@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name="orders")
@Data

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    //private List<OrderItem> orderItems=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double totalPrice;

    private LocalDateTime createdAt=LocalDateTime.now();

}