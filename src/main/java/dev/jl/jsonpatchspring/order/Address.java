package dev.jl.jsonpatchspring.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String city;
    private String street;
    @Column(name = "zip_code")
    private String zipCode;
}
