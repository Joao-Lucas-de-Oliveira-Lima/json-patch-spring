package dev.jl.jsonpatchspring.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> save(
            @RequestHeader(name = "Idempotency-Key") UUID idempotencyKey, @RequestBody OrderRequestDto newOrder) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.save(idempotencyKey,newOrder));
    }
}
