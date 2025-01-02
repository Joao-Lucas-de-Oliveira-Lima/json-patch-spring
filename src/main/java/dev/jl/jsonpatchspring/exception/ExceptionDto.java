package dev.jl.jsonpatchspring.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.jl.jsonpatchspring.exception.fielderror.FieldError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder(alphabetic = true)
public class ExceptionDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer Status;
    private String instance;
    private String detail;
    private String title;
    private List<FieldError> errors;
    private Instant timestamp;
}
