# 🧵 Threading Implementation Guide

## Overview
The Canteen System includes comprehensive **multi-threading** support using Spring's `@Async` annotation and Java's `CompletableFuture` for improved performance and responsiveness.

---

## 🎯 Threading Architecture

### **Thread Pools Configured**

1. **taskExecutor** (General Purpose)
   - Core Pool Size: 5 threads
   - Max Pool Size: 10 threads
   - Queue Capacity: 100 tasks
   - Prefix: `Canteen-Async-`

2. **emailExecutor** (Email Notifications)
   - Core Pool Size: 3 threads
   - Max Pool Size: 5 threads
   - Queue Capacity: 50 tasks
   - Prefix: `Email-`

3. **analyticsExecutor** (Analytics Processing)
   - Core Pool Size: 2 threads
   - Max Pool Size: 4 threads
   - Queue Capacity: 25 tasks
   - Prefix: `Analytics-`

4. **notificationExecutor** (Notification Handling)
   - Core Pool Size: 3 threads
   - Max Pool Size: 6 threads
   - Queue Capacity: 50 tasks
   - Prefix: `Notification-`
   - Used for asynchronous notification and email-related tasks

---

## 📦 Components Added

### 1. **AsyncConfig.java**
Configuration class defining all thread pool executors.

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() { ... }

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() { ... }

    @Bean(name = "analyticsExecutor")
    public Executor analyticsExecutor() { ... }

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor() { ... }
}
```

---

### 2. **Notification Handling**
Handles asynchronous notification logic (primarily email-related) using a dedicated executor.

```java
@Async("notificationExecutor")
public CompletableFuture<Void> handleOrderNotification(
    Order order,
    OrderStatus status)
```

**Features:**
- ✅ Sends email notifications asynchronously
- ✅ Non-blocking operation
- ✅ Error handling and logging
- ✅ Automatic thread pool management

---

### 3. **BatchOrderService.java**
Handles batch processing of multiple orders concurrently.

```java
@Async("taskExecutor")
public CompletableFuture<List<Order>> createOrdersBatch(
    List<OrderRequestDto> orderRequests)

@Async("taskExecutor")
public CompletableFuture<List<Order>> updateOrderStatusBatch(
    List<Long> orderIds, 
    OrderStatus newStatus)
```

**Features:**
- ✅ Process multiple orders in parallel
- ✅ Batch status updates
- ✅ Performance monitoring with timing logs
- ✅ Error resilience (continues on individual failures)

---

## 🔄 Updated Services

### **EmailService.java**
Uses `@Async` for non-blocking email sending.

```java
@Async("emailExecutor")
public CompletableFuture<Void> sendOrderStatusEmail(
    String toEmail, 
    Long orderId, 
    OrderStatus status)
```

**Benefits:**
- Email sending doesn't block order processing
- Multiple emails can be sent concurrently
- Faster API response times

---

### **AnalyticsService.java**
Enhanced with parallel query execution using `CompletableFuture`.

```java
public AnalyticsDto getAnalytics() {
    CompletableFuture<Long> totalOrdersFuture = ...
    CompletableFuture<Long> totalUsersFuture = ...
    CompletableFuture<Long> totalRevenueFuture = ...
    CompletableFuture.allOf(...).join();
    return analytics;
}
```

**Benefits:**
- Multiple database queries run in parallel instead of sequentially
- Significant reduction in analytics response time
- Efficient resource utilization

---

### **OrderService.java**
Uses asynchronous notification handling after major order events.

```java
// After creating order
notificationHandler.handleOrderNotification(savedOrder, OrderStatus.PENDING);

// After status update
notificationHandler.handleOrderNotification(updatedOrder, newStatus);
```

**Benefits:**
- Order creation/update completes immediately
- Notifications processed asynchronously
- Better user experience with faster response times

---

## 📊 Performance Improvements

### **Before Threading:**
```
Order Creation: High latency due to synchronous email handling
Status Update: Delayed due to blocking notification logic
Analytics Query: Sequential database queries
```

### **After Threading:**
```
Order Creation: Faster with async notifications
Status Update: Non-blocking updates
Analytics Query: Parallel database queries
```

---

## 🚀 Usage Examples

### **1. Single Order (Async Notifications)**
```bash
POST /orders
{
  "userId": 1,
  "items": [...]
}

# Response returns immediately
# Email notifications sent in background
```

### **2. Batch Order Creation**
```java
List<OrderRequestDto> orders = Arrays.asList(order1, order2, order3);

CompletableFuture<List<Order>> future = 
    batchOrderService.createOrdersBatch(orders);

List<Order> createdOrders = future.get(); // Wait for completion
```

### **3. Batch Status Update**
```java
List<Long> orderIds = Arrays.asList(1L, 2L, 3L);

CompletableFuture<List<Order>> future = 
    batchOrderService.updateOrderStatusBatch(orderIds, OrderStatus.PREPARING);

future.thenAccept(orders -> {
    System.out.println(orders.size() + " orders updated");
});
```

### **4. Parallel Analytics**
```bash
GET /analytics

# Database queries execute in parallel
```

---

## 🔍 Thread Monitoring

### **Log Output Examples:**
```
INFO  [Canteen-Async-1] Creating order for user 5
INFO  [Email-1] Sending email to john@college.edu for order 10
INFO  [Notification-1] Processing notification for order 10
INFO  [Analytics-1] Fetching analytics on thread: Analytics-1
INFO  [Email-2] Email sent successfully to john@college.edu for order 10
```

### **Thread Naming Convention:**
- `Canteen-Async-N` - General async tasks
- `Email-N` - Email operations
- `Analytics-N` - Analytics calculations
- `Notification-N` - Notification handling

---

## ⚙️ Configuration

### **application.properties**

```properties
# Async Thread Pool
spring.task.execution.pool.core-size=5
spring.task.execution.pool.max-size=10
spring.task.execution.pool.queue-capacity=25
spring.task.execution.pool.allow-core-thread-timeout=true
spring.task.execution.pool.keep-alive=60s

# Logging
logging.level.com.canteen.canteen_system.service=INFO
logging.level.org.springframework.scheduling=DEBUG
```

---

## 🎓 Threading Concepts Used

### **1. @Async Annotation**
- Spring's async execution support
- Methods run in separate threads
- Non-blocking operations

### **2. CompletableFuture**
- Java async programming
- Composable async operations
- Parallel execution support

### **3. Thread Pool Executor**
- Manages thread lifecycle
- Configurable pool sizes
- Queue management for pending tasks

### **4. Parallel Streams**
- Automatic work distribution
- Efficient use of available processors

---

## 🛡️ Error Handling

All async methods include comprehensive error handling:

```java
try {
    // Async operation
} catch (Exception e) {
    logger.error("Error: {}", e.getMessage());
}
```

**Strategy:**
- Notification failures are logged without blocking requests
- Batch operations continue on individual failures
- All errors logged with execution context

---

## 📈 Scalability Benefits

### **Concurrent Request Handling:**
- Multiple order creations processed simultaneously
- Notifications do not block request threads
- Improved throughput under load

### **Database Optimization:**
- Parallel analytics queries
- Reduced total query latency
- Better connection pool utilization

### **User Experience:**
- Faster API responses
- Smooth performance under concurrent usage

---

## 📋 Summary

### **Files Added:**
1. ✅ `AsyncConfig.java` – Thread pool configuration
2. ✅ `BatchOrderService.java` – Batch order processing

### **Files Modified:**
1. ✅ `EmailService.java`
2. ✅ `AnalyticsService.java`
3. ✅ `OrderService.java`
4. ✅ `application.properties`

### **Key Benefits:**
- ✅ Non-blocking execution
- ✅ Parallel analytics queries
- ✅ Asynchronous notifications
- ✅ Improved responsiveness

---

**Status:** ✅ Threading implemented  
**Notifications:** 📧 Email-based (async)  
**Production Ready:** ✅ Yes
