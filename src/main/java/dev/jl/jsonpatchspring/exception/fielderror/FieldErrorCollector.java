package dev.jl.jsonpatchspring.exception.fielderror;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
public class FieldErrorCollector {
    public List<FieldError> extractFieldErrors(BindingResult bindingResult){
        return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldError(
                        error.getField(),
                        error.getDefaultMessage()))
                .toList();
    }
}
