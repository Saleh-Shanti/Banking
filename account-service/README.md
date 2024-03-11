# Account Service

The Account Service is responsible for handling CRUD operations related to customer accounts.


## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Technologies](#technologies)
- [Usage](#usage)
- [Configuration](#configuration)


## Overview

The Customer Service allows users to manage customer data with secure endpoints implementing CRUD operations. It ensures data integrity and follows best practices for security.

## Prerequisites

- Java 21
- Maven 3.8.8
- Zookeeper

## Getting Started

1. Clone the repository:

    ```bash
    git clone <repository-url>
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    mvn spring-boot:run
    ```

## Technologies

- Java 21
- Spring Framework 3.2.3
- Maven 3.8.8
- Spring Security

## Endpoints

To call endpoints, basic authentication is required with the following credentials:

1. **Get Account by ID:**

   ```bash
   GET /api/v1/customers/{customerId}/accounts/{accountId}
Retrieve information about an account by providing its ID.

2. **Get Accounts by Customer ID:**
   ```bash
   GET /api/v1/customers/{customerId}/accounts
Retrieve information about accounts by providing the customer ID.

3. **Create Accounts**
   ```bash
   POST /api/v1/customers/{customerId}/accounts
Create new accounts for a customer.


4. **Update Account by ID:**
   ```bash
   PUT /api/v1/customers/{customerId}/accounts/{accountId}
Update an existing account by ID.

5. **Delete Account by ID:**
   ```bash
   DELETE /api/v1/customers/{customerId}/accounts/{accountId}
Delete an account by providing its ID.
6. **Delete Accounts by Customer ID:**
   ```bash
   DELETE /api/v1/customers/{customerId}/accounts

Delete accounts by providing the customer ID.

## Configuration
Add an external configuration file "application.properties" and include the path to it using the parameter --spring.config.location=PATH_TO_FILE/application.properties.

To call endpoints, you need basic authentication with the following credentials:

Username: Saleh
Password: Password
For all operations, you can use the following roles:

addUser for the POST method

readUser for the GET method

deleteUser for the DELETE method

updateUser for the UPDATE method

All operations require the password to be set as "Password" for successful authentication.
