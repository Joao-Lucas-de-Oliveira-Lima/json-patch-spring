package dev.jl.jsonpatchspring.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionDto{
    private Instant timestamp;
    private String message;
    private String description;
}
