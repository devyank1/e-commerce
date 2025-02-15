<h1 align="center">E-Commerce Project Architecture</h1>

<p align="center">
  <img src="images/spring-logo.png" width="300">
    <img src="images/java-logo.png" width="200">
</p>

```mermaid
classDiagram
    class User {
        +Long id
        +String username
        +String email
        +String password
        +Role role
        +List~Order~ orders
    }
    
    class Order {
        +Long id
        +Date orderDate
        +Double totalAmount
        +OrderStatus status
        +User user
        +List~OrderItem~ items
    }

    class OrderItem {
        +Long id
        +Integer quantity
        +Double price
        +Order order
        +Product product
    }

    class Product {
        +Long id
        +String name
        +String description
        +Double price
        +Category category
    }

    class Category {
        +Long id
        +String name
        +List~Product~ products
    }

    class Payment {
        +Long id
        +Date paymentDate
        +Double amount
        +Order order
    }

    class Review {
        +Long id
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
