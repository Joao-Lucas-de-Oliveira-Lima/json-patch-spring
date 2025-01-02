package dev.jl.jsonpatchspring.order.item;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ItemResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String productName;
    private Integer quantity;
    private Double price;
}