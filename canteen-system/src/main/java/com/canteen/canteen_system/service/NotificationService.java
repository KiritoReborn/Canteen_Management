package com.canteen.canteen_system.service;

import com.canteen.canteen_system.model.Order;
import com.canteen.canteen_system.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    private final EmailService emailService;

    /**
     * Send email notification asynchronously
     */
    @Async("notificationExecutor")
    public CompletableFuture<Void> sendOrderStatusNotifications(Order order, OrderStatus newStatus) {
        logger.info("Sending email notification for order {} on thread {}", 
            order.getId(), Thread.currentThread().getName());
        
        // Send email notification
        return emailService.sendOrderStatusEmail(
            order.getUser().getEmail(), 
            order.getId(), 
            newStatus
        ).whenComplete((result, throwable) -> {
            if (throwable != null) {
                logger.error("Error sending email for order {}: {}", 
                    order.getId(), throwable.getMessage());
            } else {
                logger.info("Email notification sent successfully for order {}", order.getId());
            }
        });
    }

    /**
     * Log new order creation (WebSocket removed - no frontend)
     */
    @Async("notificationExecutor")
    public CompletableFuture<Void> sendNewOrderNotification(Order order) {
        return CompletableFuture.runAsync(() -> {
            logger.info("New order {} created by user {} on thread {}", 
                order.getId(), order.getUser().getEmail(), Thread.currentThread().getName());
        });
    }
}
