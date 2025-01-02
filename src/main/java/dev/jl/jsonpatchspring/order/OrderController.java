package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
            @RequestHeader(name = "Idempotency-Key") UUID idempotencyKey,
            @Valid @RequestBody OrderRequestDto newOrder,
            BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("The provided data is missing or not in a valid format.", bindingResult);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.save(idempotencyKey, newOrder));
    }
}
