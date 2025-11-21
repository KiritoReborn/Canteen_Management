package com.canteen.canteen_system.controller;

import com.canteen.canteen_system.model.Order;
import com.canteen.canteen_system.model.OrderItem;
import com.canteen.canteen_system.repository.OrderRepository;
import com.canteen.canteen_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Scanner;
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping("/orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }


}

