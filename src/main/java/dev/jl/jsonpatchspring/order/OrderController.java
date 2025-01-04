package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> findByCriteria(
            @PageableDefault(
                    direction = Sort.Direction.ASC,
                    sort = {"orderNumber"},
                    size = 20
            )
            Pageable pageable,
            @RequestParam(required = false, name = "orderNumber")
            String orderNumber,
            @RequestParam(required = false, name = "promoCode")
            String promoCode,
            @RequestParam(required = false, name = "city")
            String city,
            @RequestParam(required = false, name = "street")
            String street,
            @RequestParam(required = false, name = "zipCode")
            String zipCode) {
        return ResponseEntity.ok(orderService.findByCriteria(
                pageable, promoCode, orderNumber, city, street, zipCode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long id){
        orderService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateById(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid OrderRequestDto update,
            BindingResult bindingResult) throws BadRequestException{
        if(bindingResult.hasErrors()){
            throw new BadRequestException("The provided data is missing or not in a valid format.", bindingResult);
        }
        return ResponseEntity.ok(orderService.updateById(id, update));
    }
}
