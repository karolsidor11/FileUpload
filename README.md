# File Upload
File Upload a simple application that provides a REST API for writing and reading files from database. 

## Overview
Application is written using Java 11, Spring Boot, JPA, running on the MySQL database. 
The application is based on hexagonal architecture.The application provides  API for saving the file  the database and searching by id or file name. 

### Building the application
```
mvn clean install
```

### Unit tests
```
mvn test
```

### Run the application 
```
mvn spring-boot:run
```
The application is available on swagger:
 http://localhost:8080/swagger-ui.html