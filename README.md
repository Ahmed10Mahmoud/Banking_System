# Banking System API

A banking backend application built with Spring Boot.

## Features

- User Registration
- JWT Authentication
- Role-Based Authorization
- Account Creation
- Deposit Money
- Withdraw Money
- Transfer Money
- Transaction History
- Global Exception Handling
- Audit Logging (AOP)

## Technologies

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- Maven
- Hibernate

## API Endpoints

### Authentication

POST /auth/register

POST /auth/login

### Accounts

POST /accounts

POST /accounts/deposit

POST /accounts/withdraw

POST /accounts/transfer

GET /accounts/my-accounts

### Transactions

GET /transactions/{accountNumber}

## Future Improvements

- Refresh Tokens
- Email Verification
- Docker
- Redis Caching
- Swagger Documentation
