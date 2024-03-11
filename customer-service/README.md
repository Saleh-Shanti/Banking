# Customer Service

The Customer Service is designed to perform CRUD operations on the customer table, providing secure endpoints using Spring Security.

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Features](#features)
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

## Features

- **CRUD Operations:** Perform Create, Read, Update, and Delete operations on customer data.
- **Spring Security:** Secure endpoints to ensure data privacy and integrity.

## Technologies

- Java 21
- Spring Framework 3.2.3
- Maven 3.8.8
- Spring Security

## Usage

To use the Customer Service, follow these steps:

1. **Create a Customer:**

   ```bash
   POST /api/v1/customers
2. **Get a Customer:** 
   ```bash
   GET /api/v1/customers/{customerId}
3. **Update a Customer:**
   ```bash
   PUT /api/v1/customers/{customerId}
4. **Delete a Customer:**
   ```bash
   DELETE /api/v1/customers/{customerId}

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
