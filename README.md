# spring_boot_projects
Here's a detailed and structured README file for your Spring Boot projects repository. This README includes an overview of the projects, technologies used, database configurations, setup instructions, and more:

---

# Spring Boot Microservices - Backend Projects

This repository contains the backend implementations of three Spring Boot projects that demonstrate RESTful API development and database integration. The projects utilize H2 (default) and MySQL databases, with profile-based configurations allowing for easy switching between them. These projects showcase various microservice-based backends, including an e-commerce product catalog system, banking system, and library management system.

### Future Projects
All future Spring Boot microservices will also be added to this repository to demonstrate various backend functionalities and integrations.

---

## Table of Contents
1. [Projects Overview](#projects-overview)
2. [Technologies Used](#technologies-used)
3. [Setup and Configuration](#setup-and-configuration)
4. [Project 1: E-Commerce Product Catalog System](#project-1-e-commerce-product-catalog-system)
5. [Project 2: Banking System](#project-2-banking-system)
6. [Project 3: Library Management System](#project-3-library-management-system)
7. [Testing](#testing)
8. [Deployment](#deployment)

---

## Projects Overview

### Project 1: E-Commerce Product Catalog System
This project is a backend system that allows users to manage an e-commerce product catalog. It supports CRUD operations on products, including the ability to filter by category and price range.

### Project 2: Banking System
This project enables the management of bank accounts, fund transfers, and transaction history. It offers REST APIs for creating accounts, transferring funds between accounts, and viewing transaction history.

### Project 3: Library Management System
This project focuses on managing library books, members, and book transactions (issue/return). It provides APIs to perform CRUD operations for books and members and track the status of issued and returned books.

---

## Technologies Used

- **Backend:** Java, Spring Boot
- **Database:** H2 (default), MySQL
- **API Testing:** Postman
- **Version Control:** GitHub

---

## Setup and Configuration

To get started with these projects, follow the steps below:

### Clone the Repository
First, clone the repository to your local machine:
```bash
git clone https://github.com/yourusername/Spring_Boot_repo.git
```

### Database Configuration (H2 and MySQL)
The projects use two databases: H2 (in-memory) for development and MySQL for production. You can switch between the two using Spring profiles.

1. **H2 Configuration (application-dev.properties):**
   - By default, the application uses H2 for development. To use H2, ensure `spring.profiles.active=dev` is set in `application.properties`.
   - The H2 console is enabled, so you can access the database at `http://localhost:8080/h2-console` using the JDBC URL: `jdbc:h2:mem:ecommerce` (for E-commerce), `jdbc:h2:mem:banking` (for Banking), or `jdbc:h2:mem:library` (for Library).

2. **MySQL Configuration (application-prod.properties):**
   - For production use MySQL, set `spring.profiles.active=prod` in `application.properties`.
   - You need to have MySQL installed and running. Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties with your MySQL configuration.

### Example Configuration:

#### Default `application.properties`:
```properties
spring.profiles.active=dev
```

#### H2 Configuration (application-dev.properties):
```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:ecommerce
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

#### MySQL Configuration (application-prod.properties):
```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Project 1: E-Commerce Product Catalog System

### Description:
A backend system to manage products in an e-commerce catalog. It supports operations such as adding, updating, deleting, and viewing products. The system also allows filtering by category or price range.

### Features:
- Add, update, and delete products.
- Retrieve product details or list of products.
- Filter products by category or price range.

### Key Classes:
- **Product Entity:** Represents the products in the catalog.
- **ProductRepository:** Repository for CRUD operations.
- **ProductService:** Service layer to handle business logic.
- **ProductController:** REST controller to handle API requests.

---

## Project 2: Banking System

### Description:
This backend system manages bank accounts, transactions, and fund transfers. It offers APIs to create accounts, transfer funds between accounts, and fetch transaction history.

### Features:
- Create and manage bank accounts.
- Transfer funds between accounts.
- View transaction history for an account.

### Key Classes:
- **Account Entity:** Represents the bank account.
- **Transaction Entity:** Represents a transaction.
- **BankingService:** Service layer to handle account operations.
- **BankingController:** REST controller to expose banking APIs.

---

## Project 3: Library Management System

### Description:
This system manages library books, members, and issue/return transactions. It provides APIs for adding books and members, as well as tracking issued and returned books.

### Features:
- Add, update, and delete books.
- Manage library members.
- Issue and return books.

### Key Classes:
- **Book Entity:** Represents the books in the library.
- **Member Entity:** Represents library members.
- **Issue Entity:** Represents book issue/return transactions.
- **LibraryService:** Service layer for library operations.
- **LibraryController:** REST controller to expose library APIs.

---

## Testing

You can test the APIs for each project using **Postman**. Below are the recommended steps:

1. **Start the Application:** Run the Spring Boot application using your IDE or with the following Maven command:
   ```bash
   mvn spring-boot:run
   ```

2. **Use Postman:**
   - Add test cases for creating products (E-commerce), bank accounts (Banking), or books (Library).
   - Test CRUD operations for each entity.
   - Test specific features like fund transfer (Banking) or issue/return of books (Library).

---

## Deployment

You can deploy the backend applications to various platforms like **Heroku**, **Render**, or **AWS Free Tier**. Below are some general deployment steps:

### 1. **Deploy on Heroku:**
   - Install the Heroku CLI.
   - Create a Heroku application: `heroku create`
   - Push the code to Heroku: `git push heroku master`
   - Set environment variables (if needed).

### 2. **Optional Frontend:**
   - You can add a simple frontend for each project using HTML/JavaScript and deploy it using **GitHub Pages** or **Netlify**.

---

## Conclusion

This repository demonstrates backend development using Spring Boot, REST API design, and database integration with both H2 and MySQL databases. Each project is fully implemented with CRUD operations, database interactions, and APIs ready for testing. The code is designed to be modular, and future microservices can be added seamlessly to the repository.
