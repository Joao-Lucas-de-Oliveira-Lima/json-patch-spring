package dev.jl.jsonpatchspring.exception;

import dev.jl.jsonpatchspring.exception.fielderror.FieldError;
import dev.jl.jsonpatchspring.exception.fielderror.FieldErrorCollector;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.misc.Interval;
import org.hibernate.query.spi.Limit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handlerException(WebRequest webRequest, Exception e) {
        String message = "An unexpected error occurred on the server. Please try again later.";
        ExceptionDto response = ExceptionDto.builder()
                .Status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .instance(webRequest.getDescription(false))
                .detail(message)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlerResourceNotFoundException(WebRequest webRequest, Exception e) {
        ExceptionDto response = ExceptionDto.builder()
                .instance(webRequest.getDescription(false))
                .Status(HttpStatus.NOT_FOUND.value())
                .title(HttpStatus.NOT_FOUND.getReasonPhrase())
                .detail(e.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(IdempotencyKeyConflictException.class)
    public ResponseEntity<ExceptionDto> handlerIdempotencyKeyConflictException(WebRequest webRequest, Exception e) {
        ExceptionDto response = ExceptionDto.builder()
                .instance(webRequest.getDescription(false))
                .Status(HttpStatus.CONFLICT.value())
                .title(HttpStatus.CONFLICT.getReasonPhrase())
                .detail(e.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handlerBadRequestException(WebRequest webRequest, BadRequestException badRequestException){
        FieldErrorCollector fieldErrorCollector = new FieldErrorCollector();
        List<FieldError> errors = fieldErrorCollector.extractFieldErrors(badRequestException.getBindingResult());
        ExceptionDto response = ExceptionDto.builder()
                .instance(webRequest.getDescription(false))
                .Status(HttpStatus.BAD_REQUEST.value())
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .detail(badRequestException.getMessage())
                .errors(errors)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
