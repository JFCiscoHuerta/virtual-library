# Virtual Library

![Java](https://img.shields.io/badge/Java-22-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green)
![License](https://img.shields.io/badge/License-Apache2-yellow)
![Build](https://img.shields.io/badge/Build-Maven-red)

## Setup


### Prerequisites
Ensure you have the following installed:
- *Java 22*
- *Maven 3.x*
- PostgreSQL (or any configured database)
- Redis

### Environment Variables
The following environment variables should be configured for the application to work correctly:

| Variable           | Description                              |
|--------------------|------------------------------------------|
| REDIS_HOST    | The hostname or IP address of the Redis server. |
| REDIS_PORT    | The port number on which the Redis server is listening. |
| POSTGRESQL_URL | The connection URL for the PostgreSQL database. |
| POSTGRESQL_USERNAME | The username for authenticating to the PostgreSQL database. |
| POSTGRESQL_PASSWORD | The password for authenticating to the PostgreSQL database. |



### Accessing the API Documentation
To access the API documentation via Swagger, make sure to run the application with the dev profile:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```


Once the application is running, you can view the Swagger documentation at:

```bash
http://{server}:{port}/swagger-ui/index.html
```


## License

This project is licensed under the  Apache 2.0 license - see the LICENSE file for detail
