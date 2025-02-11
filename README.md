# JsonPatch with Spring
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white) ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Table of Contents
- [About](#about)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Documentation](#documentation)

## About <a name="about"></a>

This is a REST API that implements a `PATCH` endpoint following the conventions defined in [RFC 6902](https://datatracker.ietf.org/doc/html/rfc6902). The API uses the `zjsonpatch` library to performe the operations.

### Supported Operations
- **`add`**: Adds a new value to the specified path.
- **`replace`**: Replaces the value at the specified path with a new value.
- **`remove`**: Removes the value at the specified path.
- **`move`**: Moves a value from one path to another.
- **`copy`**: Copies a value from one path to another.
- **`test`**: Tests whether a specified value matches the one at the given path.

### Examples
Here are some examples of JSON Patch operations:

```json
[
  { "op": "add", "path": "/fieldName", "value": "newValue" },
  { "op": "replace", "path": "/fieldName", "value": "updatedValue" },
  { "op": "remove", "path": "/fieldName" },
  { "op": "move", "from": "/sourceField", "path": "/destinationField" },
  { "op": "copy", "from": "/sourceField", "path": "/destinationField" },
  { "op": "test", "path": "/fieldName", "value": "expectedValue" }
]
```

### Patch Endpoint

```java
@PatchMapping("/{id}")
public ResponseEntity<Object> patchById(
        @Parameter(description = "ID of the resource to be modified") 
        @PathVariable(name = "id") Long id,
        @Parameter(array = @ArraySchema(schema = @Schema(implementation = JsonPatchSchema.class)),
                name = "JsonPatch",
                description = "JSON Patch representation according to RFC 6902 for partial modification.")
        @RequestBody JsonNode patch) throws IOException {
    Object responseDto = orderService.patchById(id, patch);
    return ResponseEntity
            .status(HttpStatus.OK)
            .eTag(etagService.generateEtag(responseDto))
            .body(responseDto);
}
```

### JsonPatch Validation
Validation steps:
1. Retrieve the object using the provided ID and map it to a DTO constrained by Jakarta Validation annotations.
2. Convert the object into a `JsonNode` using Jackson's `ObjectMapper` and apply the patch with `JsonPatch.apply()`.
3. Convert the patched object back to its original form using `treeToValue()`.
4. Use the `Validator` to check for constraint violations, which are handled by Spring Web's `BindingResult` and the `ExceptionHandler` for `BadRequest` errors.

### Additional Features

#### Idempotency Keys
- The application uses UUID-based idempotency keys to identify identical requests.
- Idempotency keys are stored in Redis with a TTL of 10 minutes.

Service method for idempotency key validation:
```java
@Transactional
@CachePut(value = "order", key = "#result.id")
@CacheEvict(value = "orderPage", allEntries = true)
public OrderResponseDto save(UUID idempotencyKey, OrderRequestDto newOrder) throws IdempotencyKeyConflictException {
    if (idempotencyKeyService.findById(idempotencyKey)) {
        throw new IdempotencyKeyConflictException(
            String.format(
                "The idempotency key '%s' has already been used. Please wait 10 minutes before reusing it.",
                idempotencyKey.toString()
            )
        );
    }
    Order orderSaved = orderRepository.save(mapper.mapToObject(newOrder, Order.class));
    idempotencyKeyService.save(idempotencyKey);
    return mapper.mapToObject(orderSaved, OrderResponseDto.class);
}
```

#### ETags
- ETags are included in response headers for `POST`, `PATCH`, `GET`, and `PUT` requests.
- If the `If-None-Match` header matches the current resource version, the server responds with `304 Not Modified`.

ETag generation example:
```java
public <T> String generateEtag(T object) throws IOException {
    String objectAsJsonString = objectMapper.writeValueAsString(object);
    try (InputStream inputStream = new ByteArrayInputStream(objectAsJsonString.getBytes())) {
        return generateETagHeaderValue(inputStream, false);
    }
}
```

## Getting Started <a name="getting-started"></a>

### Prerequisites
- Java JDK 21
- Docker Desktop

### Installation Steps
1. Start PostgreSQL and Redis using the `compose.yaml` file:
   ```bash
   docker compose up -d
   ```

2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Usage <a name="usage"></a>

The application will be available at: [http://localhost:8080](http://localhost:8080)

To access the API, use the following pattern:
```plaintext
http://localhost:8080/api/<resource_path>
```

## Documentation <a name="documentation"></a>

### Swagger UI
- Access the Swagger documentation at: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
- For the API documentation in JSON format: [http://localhost:8080/api/v3/api-docs](http://localhost:8080/api/v3/api-docs)
