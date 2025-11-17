package com.canteen.canteen_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Order order;
    private MenuItem menuItem;
    private int quantity;
    }