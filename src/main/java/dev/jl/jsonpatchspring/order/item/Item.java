package dev.jl.jsonpatchspring.order.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;
    @Column(name = "product_name", nullable = false)
    @NotBlank
    private String productName;
    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;
    @Column(nullable = false)
    @PositiveOrZero
    private Double price;
}
