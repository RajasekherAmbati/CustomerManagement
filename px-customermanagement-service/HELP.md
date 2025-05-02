# Getting Started


# Customer Management Service

A Spring Boot REST API to manage customers with dynamic tier calculation based on annual spend and purchase history.

---

## 🚀 How to Build and Run the Application

### Prerequisites

- Java 17+
- Maven 3.6+
- IDE (IntelliJ, VSCode, Eclipse, etc.)

### Steps
cd px-customermanagement-service
Build and run the project with below commands


sfld-rr-rambati:px-customermanagement-service rambati$ ./gradlew clean build
sfld-rr-rambati:px-customermanagement-service rambati$ ./gradlew bootRun


Application will start on


http://localhost:8080

Sample Requests to Test Endpoints
Use Postman, curl, or Swagger UI (http://localhost:8080/swagger-ui.html or /swagger-ui/index.html).

-------Create a Customer------

http://localhost:8080/v1/customer

RequestBody
{
  "name": "John",
  "email": "john@gmail.com",
   "annualSpend": 5000,
  "lastPurchaseDate": "2025-04-25T00:24:15.875032"
}


-------Update  Customer------

http://localhost:8080/v1/customer/1

{
  "name": "John",
  "email": "John@gmail.com",
  "annualSpend": 10000,
  "lastPurchaseDate": "2025-04-25T00:24:15.875032"
}

----Get Customer by ID-------

http://localhost:8080/v1/customer/id/1


----Get Customer by Name---

GET /v1/customer/byName?name=John Doe
Headers: x-user-key: 1

------Get Customer by Email------

GET /v1/customer/byEmail?email=john@example.com
Headers: x-user-key: 1


------Update Customer------

PUT /v1/customer/1
Headers: x-user-key: 1
Body:
{
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "annualSpend": 15000
}


------Delete Customer------

DELETE /v1/customer/1
Headers: x-user-key: 1



