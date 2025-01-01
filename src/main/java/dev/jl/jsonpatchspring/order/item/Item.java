package dev.jl.jsonpatchspring.order.item;

import jakarta.persistence.*;
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
    @Column(name = "product_name")
    private String productName;
    private Integer quantity;
    private Double price;
}
