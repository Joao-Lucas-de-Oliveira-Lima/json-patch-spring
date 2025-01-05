package dev.jl.jsonpatchspring.order;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.jl.jsonpatchspring.order.address.AddressDto;
import dev.jl.jsonpatchspring.order.item.ItemResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class OrderResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long Id;
    private String orderNumber;
    private String promoCode;
    private AddressDto address;
    private List<ItemResponseDto> items;
    private List<String> tags;
}
