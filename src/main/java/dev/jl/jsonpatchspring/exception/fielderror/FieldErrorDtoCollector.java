package dev.jl.jsonpatchspring.exception.fielderror;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
public class FieldErrorDtoCollector {
    public List<FieldErrorDto> extractFieldErrors(BindingResult bindingResult){
        if(bindingResult == null || bindingResult.getFieldErrors().isEmpty()){
            return null;
        }
        return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldErrorDto(
                        error.getField(),
                        error.getDefaultMessage()))
                .toList();
    }
}
