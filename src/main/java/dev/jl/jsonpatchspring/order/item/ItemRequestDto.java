package dev.jl.jsonpatchspring.order.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ItemRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "Product name cannot be blank. Please provide a valid name for the product.")
    private String productName;
    @NotNull(message = "Quantity cannot be null. Please provide a valid quantity.")
    @PositiveOrZero(message = "Quantity must be a positive value or zero. Please enter a valid quantity.")
    private Integer quantity;
    @NotNull(message = "Price cannot be null. Please provide a valid price.")
    @PositiveOrZero(message = "Price must be a positive value or zero. Please enter a valid price for the product.")
    private Double price;
}