package dev.jl.jsonpatchspring.exception;

public class IdempotencyKeyConflictException extends RuntimeException{
    public IdempotencyKeyConflictException(String message) {
        super(message);
    }
}
