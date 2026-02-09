# 📦 Project Order Management System (Java + JDBC)

This project is an **order management system** developed using **pure Java**, with **JDBC** for data persistence and a **layered architecture**, simulating a real-world **mini ERP** scenario.

The main goal of this project is to consolidate essential back-end concepts such as **relational modeling**, **business rules**, **transactions**, **referential integrity**, and **clean code organization**, without relying on frameworks.

---

## 🧠 Applied Concepts

- Pure Java (no Spring, Hibernate, or JPA)
- JDBC (PreparedStatement, ResultSet, transactions)
- Layered architecture:
  - **Model (Entities)**
  - **DAO (Data Access Object)**
  - **Service (Business rules)**
- Relational database modeling (MySQL)
- One-to-many (1:N) relationships
- Referential integrity
- Use of `Optional`
- Java Streams API (`map`, `reduce`)
- Price history persistence
- Clear separation between business logic and data access

---

## 🏗️ Project Architecture

```text
src/
 ├── model
 │    ├── Client
 │    ├── Product
 │    ├── Order
 │    └── OrderItem
 │
 ├── dao
 │    ├── ClientDAO
 │    ├── ProductDAO
 │    ├── OrderDAO
 │    └── OrderItemDAO
 │
 ├── service
 │    ├── ClientService
 │    ├── ProductService
 │    └── OrderService
 │
 ├── conn
 │    └── ConnectionFactory
 │
 ├── main
 │    └── Menu
 │
