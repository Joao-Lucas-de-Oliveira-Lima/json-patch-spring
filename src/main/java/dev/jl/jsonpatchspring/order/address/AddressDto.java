package dev.jl.jsonpatchspring.order.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String city;
    private String street;
    private String zipCode;
}
