package com.canteen.canteen_system.service;

import com.canteen.canteen_system.dto.OrderRequestDto;
import com.canteen.canteen_system.model.Order;
import com.canteen.canteen_system.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service for batch processing of orders using parallel streams and CompletableFuture
 */
@Service
@AllArgsConstructor
public class BatchOrderService {

    private static final Logger logger = LoggerFactory.getLogger(BatchOrderService.class);
    private final OrderService orderService;

    /**
     * Process multiple orders concurrently
     */
    @Async("taskExecutor")
    public CompletableFuture<List<Order>> createOrdersBatch(List<OrderRequestDto> orderRequests) {
        logger.info("Processing batch of {} orders on thread {}", 
            orderRequests.size(), Thread.currentThread().getName());
        
        long startTime = System.currentTimeMillis();
        
        // Process orders in parallel using parallel streams
        List<CompletableFuture<Order>> orderFutures = orderRequests.stream()
            .map(orderRequest -> CompletableFuture.supplyAsync(() -> {
                try {
                    return orderService.createOrder(orderRequest);
                } catch (Exception e) {
                    logger.error("Failed to create order for user {}: {}", 
                        orderRequest.getUserId(), e.getMessage());
                    return null;
                }
            }))
            .collect(Collectors.toList());
        
        // Wait for all orders to complete
        CompletableFuture<Void> allOrders = CompletableFuture.allOf(
            orderFutures.toArray(new CompletableFuture[0])
        );
        
        return allOrders.thenApply(v -> {
            List<Order> results = orderFutures.stream()
                .map(CompletableFuture::join)
                .filter(order -> order != null)
                .collect(Collectors.toList());
            
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Batch processing completed: {} orders created in {}ms", 
                results.size(), duration);
            
            return results;
        });
    }

    /**
     * Update multiple order statuses concurrently
     */
    @Async("taskExecutor")
    public CompletableFuture<List<Order>> updateOrderStatusBatch(
            List<Long> orderIds, 
            OrderStatus newStatus) {
        
        logger.info("Updating batch of {} orders to status {} on thread {}", 
            orderIds.size(), newStatus, Thread.currentThread().getName());
        
        long startTime = System.currentTimeMillis();
        
        // Update orders in parallel
        List<CompletableFuture<Order>> updateFutures = orderIds.stream()
            .map(orderId -> CompletableFuture.supplyAsync(() -> {
                try {
                    return orderService.updateOrderStatus(orderId, newStatus);
                } catch (Exception e) {
                    logger.error("Failed to update order {}: {}", orderId, e.getMessage());
                    return null;
                }
            }))
            .collect(Collectors.toList());
        
        // Wait for all updates to complete
        CompletableFuture<Void> allUpdates = CompletableFuture.allOf(
            updateFutures.toArray(new CompletableFuture[0])
        );
        
        return allUpdates.thenApply(v -> {
            List<Order> results = updateFutures.stream()
                .map(CompletableFuture::join)
                .filter(order -> order != null)
                .collect(Collectors.toList());
            
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Batch update completed: {} orders updated in {}ms", 
                results.size(), duration);
            
            return results;
        });
    }

    /**
     * Process orders by status in parallel
     */
    @Async("taskExecutor")
    public CompletableFuture<Void> processOrdersByStatus(OrderStatus status) {
        return CompletableFuture.runAsync(() -> {
            logger.info("Processing all {} orders on thread {}", 
                status, Thread.currentThread().getName());
            
            List<Order> orders = orderService.getOrdersByStatus(status);
            
            // Process each order in parallel
            orders.parallelStream().forEach(order -> {
                logger.info("Processing order {}", order.getId());
                // Add custom processing logic here
            });
            
            logger.info("Completed processing {} {} orders", orders.size(), status);
        });
    }
}
