package com.canteen.canteen_system.repository;
import com.canteen.canteen_system.model.Order;
import com.canteen.canteen_system.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findById(long orderId);
    //Order findByStatus(OrderStatus orderStatus);
}
