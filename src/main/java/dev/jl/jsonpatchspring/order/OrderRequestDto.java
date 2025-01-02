package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.order.address.Address;
import dev.jl.jsonpatchspring.order.item.Item;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String orderNumber;
    private String promoCode;
    private Address address;
    @NotNull @Valid
    private List<Item> items;
    private List<String> tags;
}
