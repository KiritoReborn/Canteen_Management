# 🍽️ College Canteen Management System

A complete **Spring Boot** backend system for managing college canteen orders with JWT authentication, email notifications, token-based order pickup, and comprehensive role-based access control.


[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue.svg)](https://www.mysql.com/)
---

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Security](#-security)
- [Database Schema](#-database-schema)
- [Testing](#-testing)
- [Project Structure](#-project-structure)

---


## 👨‍💻 Team Members and Work Distribution  

- **Raghu (BT2024029)**: User management module and multi-threading aspects.
- **Hasini (BT2024113)**: Menu management and email functionality.
- **Brinda (IMT2024013)**: Menu module and support for email features.
- **Haneesh (BT2024126)**: Analytics module and exception handling.
- **Godha (BT2024082)**: Security implementation using JWT and role-based access control.
- **Rutwik (BT2024091)**: Order management including order lifecycle, token system, and FCFS logic.

---

## 🚀 Features

### ✅ **Core Features**
- 🔐 **JWT-based Authentication** - Secure login/register with token-based sessions
- 👥 **Role-Based Access Control (RBAC)** - Student and Staff roles with granular permissions
- 🎫 **Token System** - Automatic token generation for order pickup
- 📱 **Order Status Updates** - Email notifications for order status changes
- 📧 **Email Notifications** - Automated emails on order status updates
- 📊 **Analytics Dashboard** - Comprehensive system statistics
- 🔄 **Pagination Support** - Efficient data retrieval for large datasets
- 🍔 **Menu Management** - Full CRUD operations for menu items
- 🛒 **Order Management** - Create, track, and manage orders with FCFS ordering
- 🧵 **Multi-Threading** - Parallel processing for high performance

### 🎯 **Advanced Features**
- Password encryption with BCrypt
- Custom security components for authorization
- Global exception handling
- Input validation
- MapStruct for DTO mapping
- First-Come-First-Serve (FCFS) order processing
- Multi-item order support
- Order cancellation with status validation
- **Asynchronous notifications** (Email)
- **Parallel analytics** query execution
- **Batch order processing** with CompletableFuture
- **4 dedicated thread pools** for different tasks
---

## 🛠️ Technology Stack

### **Backend**
- **Language:** Java 21
- **Framework:** Spring Boot 3.5.7
- **Security:** Spring Security + JWT
- **Database:** MySQL 8.x (Cloud-hosted on Aiven)
- **ORM:** JPA/Hibernate
- **Build Tool:** Maven
- **Email:** Spring Mail (SMTP)

### **Libraries & Tools**
- **MapStruct** - DTO mapping
- **Lombok** - Reduce boilerplate code
- **Springdoc OpenAPI** - API documentation
- **Jakarta Validation** - Input validation
- **JJWT** - JWT token handling
- **Spring Async** - Multi-threading support
- **CompletableFuture** - Asynchronous programming
- **Thread Pool Executor** - Concurrent task management

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────┐
│                  Client (Postman/Frontend)          │
└───────────────────┬─────────────────────────────────┘
                    │ HTTP + JWT
┌───────────────────▼─────────────────────────────────┐
│              Spring Boot Application                │
│  ┌──────────────────────────────────────────────┐  │
│  │         Controller Layer (REST APIs)         │  │
│  └──────────────┬───────────────────────────────┘  │
│  ┌──────────────▼───────────────────────────────┐  │
│  │         Service Layer (Business Logic)       │  │
│  └──────────────┬───────────────────────────────┘  │
│  ┌──────────────▼───────────────────────────────┐  │
│  │      Repository Layer (JPA/Hibernate)        │  │
│  └──────────────┬───────────────────────────────┘  │
└─────────────────┼───────────────────────────────────┘
                  │
┌─────────────────▼─────────────────────────────────┐
│              MySQL Database (Cloud)               │
└───────────────────────────────────────────────────┘
```

### **Layered Architecture**
1. **Controller Layer** - Handles HTTP requests/responses, validation
2. **Service Layer** - Business logic, transaction management
3. **Repository Layer** - Data access using Spring Data JPA
4. **Security Layer** - JWT authentication, authorization checks
5. **DTO Layer** - Data transfer objects with MapStruct
6. **Model Layer** - JPA entities
7. **Threading Layer** - Async execution with dedicated thread pools

---

## 🏁 Getting Started

### **Prerequisites**
- Java 21 or higher
- Maven 3.6+
- MySQL 8.x
- Git

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/KiritoReborn/javac.git
   cd javac/canteen-system
   ```

2. **Configure Database**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://your-db-host:port/canteen_db
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Configure Email (Optional)**
   ```properties
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   ```

4. **Configure JWT Secret**
   ```properties
   jwt.secret=your-secret-key-min-256-bits
   jwt.expiration=86400000
   ```

5. **Build the project**
   ```bash
   ./mvnw clean install
   ```

6. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

7. **Access the API**
   ```
   http://localhost:8080
   ```

### **Quick Test**
```bash
# Register a new user
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@college.edu",
    "password": "password123",
    "role": "Student"
  }'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@college.edu",
    "password": "password123"
  }'
```

---

## 📚 API Documentation

### **Authentication Endpoints** (Public)

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/auth/register` | Register new user | `{ name, email, password, role }` |
| POST | `/auth/login` | Login and get JWT token | `{ email, password }` |

### **Menu Endpoints**

| Method | Endpoint | Auth | Role | Description |
|--------|----------|------|------|-------------|
| GET | `/api/menu/` | No | - | Get all menu items |
| GET | `/api/menu/{itemname}` | No | - | Get item by name |
| GET | `/api/menu/byId/{id}` | No | - | Get item by ID |
| POST | `/api/menu` | Yes | Staff | Add menu item |
| PUT | `/api/menu/{id}` | Yes | Staff | Update menu item |
| DELETE | `/api/menu/{id}` | Yes | Staff | Delete menu item |

### **Order Endpoints**

| Method | Endpoint | Auth | Role | Description |
|--------|----------|------|------|-------------|
| GET | `/orders` | Yes | Staff | Get all orders (paginated) |
| GET | `/orders/{id}` | Yes | Staff/Owner | Get order by ID |
| GET | `/orders/user/{userId}` | Yes | Staff/Owner | Get user's orders |
| GET | `/orders/status/{status}` | Yes | Staff | Get orders by status |
| GET | `/orders/token/{tokenNumber}` | Yes | - | Get order by token |
| POST | `/orders` | Yes | - | Create new order |
| PUT | `/orders/{id}/status` | Yes | Staff | Update order status |
| POST | `/orders/pickup/{tokenNumber}` | Yes | Staff | Mark order as picked up |
| DELETE | `/orders/{id}/cancel` | Yes | Staff/Owner | Cancel order |
| DELETE | `/orders/{id}` | Yes | Staff | Delete order |

### **User Endpoints**

| Method | Endpoint | Auth | Role | Description |
|--------|----------|------|------|-------------|
| GET | `/users` | Yes | Staff | Get all users |
| GET | `/users/id/{id}` | Yes | Staff/Owner | Get user by ID |
| GET | `/users/name/{name}` | Yes | Staff | Get user by name |
| GET | `/users/role/{role}` | Yes | Staff | Get users by role |
| PUT | `/users/{id}` | Yes | Staff/Owner | Update user |
| POST | `/users/{id}/change-password` | Yes | Owner | Change password |
| DELETE | `/deleteuser` | Yes | Owner | Delete own account |

### **Analytics Endpoints**

| Method | Endpoint | Auth | Role | Description |
|--------|----------|------|------|-------------|
| GET | `/analytics` | Yes | Staff | Get system analytics |

### **Sample Requests**

#### Create Order
```json
POST /orders
Authorization: Bearer <your-jwt-token>

{
  "userId": 1,
  "items": [
    {
      "menuItemId": 1,
      "quantity": 2
    },
    {
      "menuItemId": 3,
      "quantity": 1
    }
  ]
}
```

#### Response
```json
{
  "id": 5,
  "userId": 1,
  "userName": "John Doe",
  "tokenNumber": "TKN12345",
  "status": "PENDING",
  "totalPrice": 150.00,
  "createdAt": "2025-11-25T10:30:00",
  "items": [
    {
      "menuItemId": 1,
      "menuItemName": "Sandwich",
      "quantity": 2,
      "itemPrice": 50.00
    },
    {
      "menuItemId": 3,
      "menuItemName": "Coffee",
      "quantity": 1,
      "itemPrice": 50.00
    }
  ]
}
```

---

## 🔐 Security

### **JWT Authentication**
- Tokens expire after 24 hours (configurable)
- Bearer token format: `Authorization: Bearer <token>`
- Password encryption using BCrypt
- Stateless session management

### **Role-Based Access Control**

#### **Staff Permissions**
- ✅ View all orders and users
- ✅ Manage menu items (add/edit/delete)
- ✅ Update order status
- ✅ Mark orders as picked up
- ✅ View analytics
- ✅ Delete orders

#### **Student Permissions**
- ✅ View menu items
- ✅ Create orders
- ✅ View own orders only
- ✅ Cancel own pending orders
- ✅ View own profile
- ✅ Update own profile
- ❌ Cannot view other users' orders
- ❌ Cannot manage menu
- ❌ Cannot access analytics

### **Security Components**
- `UserSecurity` - Validates user ownership
- `OrderSecurity` - Validates order ownership
- `JwtAuthenticationFilter` - Validates JWT tokens
- `@PreAuthorize` annotations for endpoint protection

---

## 🗄️ Database Schema

### **Entity Relationships**

```
User (1) ──────< (Many) Order
                    │
                    │ (1)
                    │
                    └──< (Many) OrderItem >──(Many) MenuItem
                    │
                    │ (1)
                    │
                    └──< (1) OrderToken
```

### **Tables**

#### **users**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(255)
email VARCHAR(255) UNIQUE
password VARCHAR(255)
role ENUM('Student', 'Staff')
created_at TIMESTAMP
```

#### **menu_items**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
itemname VARCHAR(255)
price DOUBLE
category VARCHAR(255)
available BOOLEAN
```

#### **orders**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
user_id BIGINT (FK -> users.id)
status ENUM('PENDING', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED')
total_price DOUBLE
created_at TIMESTAMP
```

#### **order_items**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
order_id BIGINT (FK -> orders.id)
menu_item_id BIGINT (FK -> menu_items.id)
quantity INT
item_price DOUBLE
```

#### **order_tokens**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
order_id BIGINT (FK -> orders.id)
token_number VARCHAR(255) UNIQUE
status ENUM('ACTIVE', 'PICKED_UP', 'EXPIRED')
issued_at TIMESTAMP
picked_up_at TIMESTAMP
```

---

## ⚡ Performance & Threading

### **Thread Pool Configuration**
The system uses **4 dedicated thread pools** to ensure high performance and isolate different tasks:

| Thread Pool | Core | Max | Queue | Purpose |
|-------------|------|-----|-------|---------|
| `taskExecutor` | 5 | 10 | 100 | General asynchronous tasks |
| `emailExecutor` | 3 | 5 | 50 | Handling SMTP email transmission |
| `analyticsExecutor` | 2 | 4 | 25 | Parallel processing for analytics queries |
| `notificationExecutor` | 3 | 6 | 50 | Processing order status alerts (Email logic) |

### **Performance Improvements**
- ⚡ **Improved performance** using asynchronous processing
- 🔄 **Batch processing** support for multiple orders
- 📧 **Non-blocking** email notifications

### **Key Threading Features**
- `@Async` methods for non-blocking operations
- `CompletableFuture` for parallel processing
- Parallel analytics query execution
- Batch order creation and updates
- Thread-safe email notification handling 

See [Threading Guide](THREADING_GUIDE.md) for detailed implementation.

---

## 🧪 Testing

### **Using Postman**
1. Import the Postman collection from `/postman/collections/`
2. Set environment variables
3. Run the collection

### **Test Flow**
1. **Register** a student and staff user
2. **Login** to get JWT tokens
3. **Create menu items** (as staff)
4. **Create orders** (as student)
5. **Update order status** (as staff)
6. **Verify email notifications**
7. **Test pickup with token**
8. **Monitor thread execution** in logs

### **Performance Testing**
```bash
# Test concurrent order creation
for i in {1..10}; do
  curl -X POST http://localhost:8080/orders \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"userId": 1, "items": [...]}' &
done
wait
```

### **Running Unit Tests**
```bash
./mvnw test
```

---

## 📁 Project Structure

```
canteen-system/
├── src/
│   ├── main/
│   │   ├── java/com/canteen/canteen_system/
│   │   │   ├── config/           # Async and application configuration
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── exception/        # Custom exceptions
│   │   │   ├── mapper/           # MapStruct mappers
│   │   │   ├── model/            # JPA entities
│   │   │   ├── repository/       # Spring Data repositories
│   │   │   ├── security/         # Security components
│   │   │   └── service/          # Business logic & async services
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Unit tests
├── postman/                      # Postman collections
├── pom.xml
└── README.md
```

---

## 🎯 Order Status Flow

```
PENDING ──> PREPARING ──> READY ──> COMPLETED
    │
    └──> CANCELLED (only from PENDING)
```

**Status Descriptions:**
- **PENDING** - Order created, awaiting preparation
- **PREPARING** - Kitchen is preparing the order
- **READY** - Order ready for pickup (email sent)
- **COMPLETED** - Order picked up by customer
- **CANCELLED** - Order cancelled (only from PENDING)

---

## 📊 Analytics Response

```json
{
  "totalOrders": 156,
  "totalRevenue": 12450.00,
  "pendingOrders": 5,
  "preparingOrders": 8,
  "readyOrders": 3,
  "completedOrders": 140,
  "cancelledOrders": 11,
  "totalUsers": 89,
  "totalMenuItems": 25
}
```


## 🎓 Use Cases

Perfect for:
- College projects
- Learning Spring Boot
- Understanding JWT authentication
- Backend application development
- Role-based access control implementation
- RESTful API design

---

## 📈 Features Roadmap

- [x] JWT Authentication
- [x] Role-Based Access Control
- [x] Token System
- [x] Email Notifications
- [x] Analytics Dashboard
- [x] Pagination Support
- [x] Multi-Threading & Async Processing
- [x] Batch Order Processing
- [x] Parallel Analytics Queries
- [ ] Payment Gateway Integration
- [ ] QR Code Scanning
- [ ] Mobile Application
- [ ] Push Notifications
- [ ] Inventory Management

---

## 🚀 Quick Start Commands

```bash
# Clone
git clone https://github.com/KiritoReborn/javac.git

# Navigate
cd javac/canteen-system

# Build
./mvnw clean install

# Run
./mvnw spring-boot:run

# Test
curl http://localhost:8080/api/menu/
```
