<h1 align="center">E-Commerce Project Architecture</h1>

<p align="center">
  <img src="images/spring-logo.png" width="300">
</p>

<h2>Class Diagram:</h2>

```mermaid
classDiagram
    class User {
        +String username
        +String email
        +String password
        +Role role
        +List~Order~ orders
    }
    
    class Order {
        +Date orderDate
        +Double totalAmount
        +OrderStatus status
        +User user
        +List~OrderItem~ items
    }

    class OrderItem {
        +Integer quantity
        +Double price
        +Order order
        +Product product
    }

    class Product {
        +String name
        +String description
        +Double price
        +Category category
    }

    class Category {
        +String name
        +List~Product~ products
    }

    class Payment {
        +Date paymentDate
        +Double amount
        +Order order
    }

    class Review {
        +String comment
        +Integer rating
        +User user
        +Product product
    }

    User "1" --> "0..*" Order : Possui
    Order "1" --> "0..*" OrderItem : Contém
    OrderItem "1" --> "1" Product : Refere-se
    Product "1" --> "0..*" Review : Possui
    User "1" --> "0..*" Review : Escreve
    Product "1" --> "1" Category : Pertence a
    Order "1" --> "0..1" Payment : Possui
