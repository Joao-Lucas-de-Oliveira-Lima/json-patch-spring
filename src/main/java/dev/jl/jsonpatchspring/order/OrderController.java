package dev.jl.jsonpatchspring.order;

import com.fasterxml.jackson.databind.JsonNode;
import dev.jl.jsonpatchspring.exception.BadRequestException;
import dev.jl.jsonpatchspring.utils.etag.EtagService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final EtagService etagService;

    public OrderController(OrderService orderService, EtagService etagService) {
        this.orderService = orderService;
        this.etagService = etagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> save(
            @RequestHeader(name = "Idempotency-Key") UUID idempotencyKey,
            @Valid @RequestBody OrderRequestDto newOrder,
            BindingResult bindingResult) throws BadRequestException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("The provided data is missing or not in a valid format.", bindingResult);
        }
        OrderResponseDto responseDto = orderService.save(idempotencyKey, newOrder);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(etagService.generateEtag(responseDto))
                .body(responseDto);
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
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long id) {
        orderService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateById(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid OrderRequestDto update,
            BindingResult bindingResult) throws BadRequestException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("The provided data is missing or not in a valid format.", bindingResult);
        }
        OrderResponseDto responseDto = orderService.updateById(id, update);
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(etagService.generateEtag(responseDto))
                .body(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchById(
            @PathVariable(name = "id") Long id,
            @RequestBody JsonNode patch) throws IOException {
        Object responseDto = orderService.patchById(id, patch);
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(etagService.generateEtag(responseDto))
                .body(responseDto);
    }
}
