package com.canteen.canteen_system.service;

import com.canteen.canteen_system.model.Order;
import com.canteen.canteen_system.repository.OrderRepository;
import com.canteen.canteen_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
public class OrderService {
    public final OrderRepository orderRepository;
    public final UserRepository userRepository;

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public Order PlaceOrder(Order order){
        return orderRepository.save(order);
}

}
