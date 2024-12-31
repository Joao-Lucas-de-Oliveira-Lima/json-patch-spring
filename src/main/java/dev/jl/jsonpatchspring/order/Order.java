package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.order.item.Item;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long Id;
    @Column(name = "order_number", nullable = false)
    private String orderNumber;
    @Column(name = "promo_code")
    private String promoCode;
    @ElementCollection
    @CollectionTable(
            name = "order_tag",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<String> tags;
    @Embedded
    private Address address;
    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<Item> items;
}
