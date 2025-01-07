package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.order.address.AddressDto;
import dev.jl.jsonpatchspring.order.item.Item;
import dev.jl.jsonpatchspring.order.item.ItemRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderRequestDto implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "Order number cannot be blank. Please provide a valid order number.")
    private String orderNumber;
    private String promoCode;
    private AddressDto address;
    @Valid
    @NotEmpty(message = "Items list cannot be empty. Please provide at least one item in the order.")
    private Set<ItemRequestDto> items;
    private Set<String> tags;

    @Override
    public OrderRequestDto clone() {
        try {
            OrderRequestDto clone = (OrderRequestDto) super.clone();
            clone.setAddress(this.address.clone());
            Set<ItemRequestDto> cloneItems = this.items.stream()
                    .map(ItemRequestDto::clone)
                    .collect(Collectors.toSet());
            clone.setItems(cloneItems);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed: " + e.getMessage(), e);
        }
    }
}
