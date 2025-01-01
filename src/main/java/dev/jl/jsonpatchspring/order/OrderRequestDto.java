package dev.jl.jsonpatchspring.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrderRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
