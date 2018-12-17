# Spring Boot, MySQL,Envers Spring Data Rest API Tutorial

Build Restful CRUD API for a simple json config application using Spring Boot, Mysql, Spring data, and Envers.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 5.x.x

## Steps to Setup

**1. Clone the application**

```

**2. Create Mysql database**
```bash
create database configdb
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

mvn package
java -jar target/json-config-1.0.0.jar

or using your specifc properties file:
java -jar target\json-config-1.0.0.jar --spring.config.location=C:\local.properties

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

Access Swagger-UI at <http://localhost:8080/json-config/swagger-ui.html>

## Explore Rest APIs

The app defines following CRUD APIs.

    GET /json-config/api
    
    POST /json-config/api
    
    GET /json-config/api/{id}
    
    PUT /json-config/api/{id}
    
    DELETE /json-config/api/{id}

You can test them using postman or any other rest client.

## Learn more

