package dev.jl.jsonpatchspring.exception.fielderror;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldError implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    String field;
    String message;
}
