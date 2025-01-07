package dev.jl.jsonpatchspring.order.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String city;
    private String street;
    private String zipCode;

    @Override
    public AddressDto clone() {
        try {
            return (AddressDto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
