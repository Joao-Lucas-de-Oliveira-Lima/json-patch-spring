# JsonPatch with Spring
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)

- [About](#about)
- [Getting Started](#getting_started)
- [Usage](#usage)
- [Documentation](#documentation)

## About <a name = "about"></a>

This is a REST API that implements a `PATCH` endpoint, following the conventions defined in [RFC 6902](https://datatracker.ietf.org/doc/html/rfc6902). It uses the `zjsonpatch` library to perform the following operations:

- `add`
- `replace`
- `remove`
- `move`
- `copy`
- `test`

### JsonPatch Validation

Validation is performed by first retrieving the object corresponding to the provided ID and mapping it to a DTO constrained by Jakarta Validation annotations. The object is then converted into a `JsonNode` using `convertToValue()` from Jackson's `ObjectMapper`, and the patch is applied using `JsonPatch.apply()`. After applying the patch, the object is converted back to its original form using `treeToValue()`. The `Validator` is then used to check for any constraint violations. Any validation errors are captured and stored in an object implementing Spring Web's `BindingResult`, which is subsequently processed by the `ExceptionHandler` to handle `BadRequest` errors.


### Additional Features

- **Idempotency Keys:** The application uses UUID-based idempotency keys to identify "identical" requests (only the UUID is verified). Each idempotency key is stored in a Redis database with a TTL of 10 minutes.

- **ETags:** ETags are included in the response headers using Spring Web's `ShallowEtagHttpFilter`. They are sent in the headers of responses for `POST`, `PATCH`, `GET` (Get by ID), and `PUT` requests. When a request includes the `If-None-Match` header and the ETag matches the current resource version, the server responds with a `304 Not Modified` status and an empty body, indicating that the resource has not changed since the last request.

## Getting Started <a name = "getting_started"></a>

### Prerequisites

- Java JDK 21
- Docker Desktop

### Installing

1. Start PostgreSQL and Redis using `compose.yaml`:

```bash
docker compose up -d
```

2. Compile the application:

```bash
./mvnw compile
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

## Usage <a name = "usage"></a>

The application will be available at: [http://localhost:8080](http://localhost:8080)

To access the API, use the following pattern:

```plaintext
http://localhost:8080/api/<resource_path>
```

## Documentation <a name = "documentation"></a>

### Swagger UI

- To access the Swagger documentation, visit: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
- For the API documentation in JSON format, use: [http://localhost:8080/api/v3/api-docs](http://localhost:8080/api/v3/api-docs)

---
