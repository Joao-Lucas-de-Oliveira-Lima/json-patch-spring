package dev.jl.jsonpatchspring.order.item;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;
    @Column(name = "product_name")
    private String productName;
    private Integer quantity;
    private Double price;
}
