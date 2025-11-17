package com.canteen.canteen_system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum OrderStatus {
    PENDING,
    COMPLETED,
    CANCELLED
}

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Order {
    
    private Long id;
    private User user;  
    private List<OrderItem> orderItems = new ArrayList<>();
    private OrderStatus status;
    private double totalPrice; 
    private LocalDateTime createdAt;
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}