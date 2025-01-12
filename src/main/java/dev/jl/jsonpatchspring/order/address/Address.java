package dev.jl.jsonpatchspring.order.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address{
    private String city;
    private String street;
    @Column(name = "zip_code")
    private String zipCode;
}
