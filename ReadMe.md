**Customer and Account Microservices**

**Overview**

This project comprises two microservices - Customer and Account, designed to manage bank customer and account information. The services are developed using Spring Boot 3.2.3, Java 21 and Spring Data JPA.

**Project Structure**

The project follows a modular structure, with unified packages for model, repository and service for each. Each microservice includes shared models or DTOs for inter-service communication.

**Customer Microservice**

- **Model:** **Customer** entity class.
- **Repository:** Spring Data JPA repository for CRUD operations.
- **Service:** Business logic for customer-related operations.
- **Controller:** RESTful APIs for customer operations.
- **Validation:** Custom validation classes.
- **Test:** Unit tests for services and controllers.

**Account Microservice**

- **Model:** **Account** entity class.
- **Repository:** Spring Data JPA repository for CRUD operations.
- **Service:** Business logic for account-related operations.
- **Controller:** RESTful APIs for account operations.
- **Validation:** Custom validation classes.

**Shared Model**

- Contains shared models and DTOs  used by both microservices.

**Spring Boot Features**

- **Spring Data JPA:** JPA for data access.
- **Spring Security :** Security for authorization and authentication.
- **Validation:** Input validation using annotations and custom validators.
- **Exception Handling:** Global exception handling.
- **Logging:** Utilization of Spring Boot's logging capabilities.

**Testing**

- Unit tests implemented for services and controllers.
- Targeting at least 70% test coverage.

**OpenAPI Specification**

- Springdoc (Spring Boot OpenAPI) used for generating OpenAPI documentation.


**Database**

- Relational database MSSQL for storing customer and account data.


**Design Decisions**

- **Modular Structure:** Adopted a modular structure for better organization and maintainability.
- **Spring Data JPA:** Utilized Spring Data JPA for simplified data access.
- **Spring Security:** Incorporated Spring Security for API security.

**Shortcomings**

- **Limited Error Handling:** Exception handling is limited; further refinement is needed.
- **Simplified Security:** Basic authentication or JWT is used for simplicity; additional security measures may be required based on production needs.

**Assumptions**

- **Assumed Basic Authentication:** Assumes basic authentication for simplicity.
- **Simplified Validation:** Basic input validation is implemented.
- **Relational Database:** Assumes the use of a relational database for storing data.

**How to Run Locally**

1. Clone the repository.
1. Navigate to the root directory.
1. Run **mvn clean install** to build the project.
1. Execute the generated JAR files for both microservices.
1. Access the APIs as per the OpenAPI documentation.

Refer to each microservice's specific Readme for detailed instructions.

