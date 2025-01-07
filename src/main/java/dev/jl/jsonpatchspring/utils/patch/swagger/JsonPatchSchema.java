package dev.jl.jsonpatchspring.utils.patch.swagger;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonPatchSchema {
    @Enumerated
    @NotNull
    private OperationType op;
    @NotNull
    private String path;
    private String from;
    private Object value;
}
