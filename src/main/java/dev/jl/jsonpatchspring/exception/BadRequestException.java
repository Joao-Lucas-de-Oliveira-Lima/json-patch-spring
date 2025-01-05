package dev.jl.jsonpatchspring.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.BindingResult;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException{
    private BindingResult bindingResult;

    public BadRequestException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BadRequestException(String message) {
        super(message);
    }
}
