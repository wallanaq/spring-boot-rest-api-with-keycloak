# spring-boot-auth-keycloak

Spring Boot Rest API with Keycloak

### Stack

- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database
- Swagger UI (Springfox)
- Lombok

### Keycloak server

```
docker-compose up -d
```

### Run

```
git clone https://github.com/wallanaq/spring-boot-auth-keycloak.git
cd spring-boot-auth-keycloak
mvn spring-boot:run
```

```
git clone https://github.com/wallanaq/spring-boot-auth-keycloak.git
cd spring-boot-auth-keycloak
mvn package
java -jar target/spring-boot-auth-keycloak-0.0.1-SNAPSHOT.jar
```

### Swagger UI

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

