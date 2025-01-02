package dev.jl.jsonpatchspring.exception;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handlerException(WebRequest webRequest, Exception e) {
        String message = "An unexpected error occurred on the server. Please try again later.";
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponse(webRequest, message));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlerResourceNotFoundException(WebRequest webRequest, Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildResponse(webRequest, e.getMessage()));
    }

    @ExceptionHandler(IdempotencyKeyConflictException.class)
    public ResponseEntity<ExceptionDto> handlerIdempotencyKeyConflictException(WebRequest webRequest, Exception e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildResponse(webRequest, e.getMessage()));
    }

    private ExceptionDto buildResponse(WebRequest webRequest, String message) {
        return ExceptionDto.builder()
                .timestamp(Instant.now())
                .message(message)
                .description(webRequest.getDescription(false))
                .build();
    }

    /*private String getStackTraceAsString(Exception e){
        return Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(System.lineSeparator(), "StackTrace:", ""));
    }*/
}
