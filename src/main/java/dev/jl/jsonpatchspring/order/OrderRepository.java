package dev.jl.jsonpatchspring.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT o FROM Order o
            WHERE (LOWER(o.orderNumber) LIKE LOWER(CONCAT('%', :orderNumber, '%')) OR :orderNumber IS NULL)
            AND (LOWER(o.promoCode) LIKE LOWER(CONCAT('%', :promoCode, '%')) OR :promoCode IS NULL)
            AND (LOWER(o.address.city) LIKE LOWER(CONCAT('%', :city, '%')) OR :city IS NULL)
            AND (LOWER(o.address.street) LIKE LOWER(CONCAT('%', :street, '%')) OR :street IS NULL)
            AND (LOWER(o.address.zipCode) LIKE LOWER(CONCAT('%', :zipCode, '%')) OR :zipCode IS NULL)
            """)
    Page<Order> findByCriteria(Pageable pageable,
                               @Param(value = "orderNumber") String orderNumber,
                               @Param(value = "promoCode") String promoCode,
                               @Param(value = "city") String city,
                               @Param(value = "street") String street,
                               @Param(value = "zipCode") String zipCode);
}

