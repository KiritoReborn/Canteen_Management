# Software Requirements Specification (SRS)

**Project Title:** College Canteen Ordering & Token Management System

**Date:** November 21, 2025

---

## 1. Introduction

### 1.1 Purpose
To create a **College Canteen Ordering & Token Management System** that allows students and staff to place food orders online, receive digital tokens, and track their order status in real-time.

### 1.2 Scope
The system will provide the following functionalities:
- User registration and authentication with role-based access
- Browse and search menu items with availability status
- Place orders with multiple items and receive unique tokens
- Real-time order status tracking
- Order history and management
- Menu management for administrators
- User profile management

The backend will be built in Java + Spring Boot with JPA/Hibernate, and data will be stored using MySQL database.

### 1.3 Intended Users
- **Students & Staff:** Browse menu, place orders, track orders
- **Canteen Staff:** View orders, update status
- **Administrators:** Manage menu, users, and view analytics

---

## 2. System Overview

The system follows a RESTful API architecture with layered approach:
- **Controller Layer:** Handles HTTP requests/responses
- **Service Layer:** Business logic and validations
- **Repository Layer:** Data persistence using JPA
- **Model Layer:** Entities (User, MenuItem, Order, OrderItem)
- **DTO Layer:** Data transfer objects

---

## 3. System Features

### 3.1 User Management & Authentication
- User registration with email validation
- Login with credential verification
- Role-based access control (Student, Staff, Admin, Canteen_Staff)
- Password change functionality
- User profile updates
- Account deletion

### 3.2 Menu Management
- View all available menu items
- Search items by name or category
- Add/Update/Delete menu items (Admin only)
- Mark items as available/unavailable

### 3.3 Order Processing & Token System
- Create orders with multiple items
- Automatic total price calculation
- Generate unique token for each order
- Track order status (PENDING → PREPARING → READY → COMPLETED)
- View order history
- Cancel orders

### 3.4 Analytics & Reporting (Admin)
- Sales reports
- Popular items analysis
- Revenue tracking

---

## 4. API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /register | Register new user |
| POST | /login | User login |

### User Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /users | Get all users |
| GET | /users/id/{id} | Get user by ID |
| PUT | /users/{id} | Update user profile |
| POST | /users/{id}/change-password | Change password |
| DELETE | /deleteuser | Delete account |

### Menu Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/menu/ | Get all menu items |
| GET | /api/menu/{itemname} | Get item by name |
| POST | /api/menu | Add menu item |
| PUT | /api/menu/{id} | Update menu item |
| DELETE | /api/menu/{id} | Delete menu item |

### Order Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /orders | Get all orders |
| POST | /orders | Create new order |
| PUT | /orders/{id}/status | Update order status |
| DELETE | /orders/{id} | Cancel order |

---

## 5. Data Storage

### 5.1 Database Type
- **MySQL Database** (Cloud-hosted on Aiven)
- JPA/Hibernate ORM for object-relational mapping

### 5.2 Table Examples

**Users Table:**
```
id | name | email | password | role | created_at
```

**Menu Items Table:**
```
id | itemname | price | category | available
```

**Orders Table:**
```
id | user_id | status | total_price | created_at
```

**Order Items Table:**
```
id | order_id | menu_item_id | quantity | item_price
```

---

## 6. Data Model (Classes)

### User
- id : Long
- name : String
- email : String (Unique)
- password : String
- role : Role Enum
- createdAt : LocalDateTime

### MenuItem
- id : Long
- itemname : String
- price : double
- category : String
- available : boolean

### Order
- id : Long
- user : User
- orderItems : List<OrderItem>
- status : OrderStatus Enum
- totalPrice : double
- createdAt : LocalDateTime

### OrderItem
- id : Long
- order : Order
- menuItem : MenuItem
- quantity : int
- itemPrice : double

---

## 7. Non-Functional Requirements

- **Performance:** API response under 500ms
- **Security:** Password encryption, JWT authentication, RBAC
- **Reliability:** 99.5% uptime during operational hours
- **Maintainability:** Clean architecture, 80% test coverage
- **Usability:** RESTful design, Swagger documentation

---

## 8. Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.7
- **Database:** MySQL 8.x
- **Build Tool:** Maven
- **Libraries:** MapStruct, Lombok, Springdoc OpenAPI
- **Testing:** JUnit, Postman

---

## 9. Sample Input/Output

### POST /register
**Request:**
```json
{
  "name": "John Doe",
  "email": "john@college.edu",
  "password": "SecurePass123",
  "role": "Student"
}
```
**Response:**
```json
{
  "status": "success",
  "message": "User registered successfully"
}
```

### POST /login
**Request:**
```json
{
  "email": "john@college.edu",
  "password": "SecurePass123"
}
```
**Response:**
```json
{
  "status": "success",
  "message": "Login Successful"
}
```

### POST /api/menu
**Request:**
```json
{
  "itemname": "Sandwich",
  "price": 45.00,
  "category": "Breakfast",
  "available": true
}
```
**Response:**
```json
{
  "id": 5,
  "itemname": "Sandwich",
  "price": 45.00,
  "category": "Breakfast",
  "available": true
}
```

---

## 10. Assumptions

- All users have valid college email addresses
- Internet connectivity available for cloud database
- Orders placed during operational hours (7 AM - 7 PM)
- Payment is cash-on-pickup
- Token numbers are unique

---

## 11. Limitations

- Spring Security currently disabled
- Passwords stored in plain text (needs BCrypt)
- No JWT authentication yet
- Token system not implemented
- No payment gateway integration
- No mobile application
- Limited analytics features

---

## 12. Future Enhancements

- Payment gateway integration
- Mobile application
- QR code-based token scanning
- Push notifications
- Advanced analytics dashboard
- Inventory management
- Loyalty rewards system

---

**Document Version:** 1.0  
**Status:** Active Development
