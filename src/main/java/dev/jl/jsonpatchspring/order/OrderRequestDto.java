package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.order.address.AddressDto;
import dev.jl.jsonpatchspring.order.item.ItemRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "Order number cannot be blank. Please provide a valid order number.")
    private String orderNumber;
    private String promoCode;
    private AddressDto address;
    @Valid
    @NotEmpty(message = "Items list cannot be empty. Please provide at least one item in the order.")
    private List<ItemRequestDto> items;
    private List<String> tags;
}
