package dev.jl.jsonpatchspring.order.item;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double price;
    @Column(name = "last_modified")
    @LastModifiedDate
    private Instant lastModified;
}
