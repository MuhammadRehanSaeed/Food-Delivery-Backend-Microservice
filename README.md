# 🍽️ Food Ordering & Delivery Backend — Spring Boot Microservices

This project is a scalable **Food Ordering & Delivery backend** built using a complete **Spring Boot Microservices** architecture.  
It includes secure authentication using **JWT**, centralized validation with **OAuth2**, service discovery through **Eureka**, and inter-service communication via **Feign Client**.

---

## 🧩 Microservices Overview

### 🔐 1️⃣ User-Service
- User Registration & Login  
- Generates **JWT** on successful authentication  
- Stores user data  
- Validates all incoming requests via OAuth2 (through Gateway)  

---

### 🏪 2️⃣ Restaurant-Service
- Add & manage restaurants  
- Fetch restaurant details  
- Validates all requests via OAuth2  
- Communicates with Menu-Service  

---

### 🍛 3️⃣ Menu-Service
- Add menu items for a specific **restaurantId**  
- Fetch menu by restaurant  
- Uses **Feign Client** to communicate with Restaurant-Service  
- Validates all requests with OAuth2  

---

### 🛒 4️⃣ Order-Service
- Places food orders by communicating with:  
  - **User-Service** (validate user)  
  - **Restaurant-Service** (validate restaurant)  
  - **Menu-Service** (validate menu items)  
- Inter-service communication using **Feign**  
- Centralized JWT validation using OAuth2  

---

## 🧰 Supporting Services

### 🗺️ Eureka Server
- Centralized service registry  
- All microservices register here  
- Enables load balancing and discovery  

---

### 🔀 API Gateway
- Single entry point for all client requests  
- Validates **JWT Tokens** using OAuth2  
- Routes traffic to correct microservice  
- Blocks unauthorized requests  

---

## 🔐 Security Architecture

### ✔ JWT Authentication Flow
1. User logs in → User-Service generates JWT  
2. Client sends JWT with all requests  
3. API Gateway verifies JWT using OAuth2 Resource Server  
4. Internal services (**User, Restaurant, Menu, Order**) also validate JWT via OAuth2  

### ✔ OAuth2 Resource Server
- All microservices enforce token validation  
- Ensures secure service-to-service communication  

---

## 🛠️ Tech Stack

- Spring Boot  
- Spring Cloud (Gateway, Eureka)  
- Spring Security + OAuth2 Resource Server  
- JWT Authentication  
- Spring Data JPA  
- MySQL  
- Feign Client  
- Maven  
- Lombok  

---

