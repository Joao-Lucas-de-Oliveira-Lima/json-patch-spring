package dev.jl.jsonpatchspring.order;

import com.fasterxml.jackson.databind.JsonNode;
import dev.jl.jsonpatchspring.exception.BadRequestException;
import dev.jl.jsonpatchspring.exception.ExceptionDto;
import dev.jl.jsonpatchspring.utils.etag.EtagService;
import dev.jl.jsonpatchspring.utils.patch.swagger.JsonPatchSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
@Tag(name = "OrderController", description = "Operations related to order management.")
public class OrderController {
    private final OrderService orderService;
    private final EtagService etagService;

    public OrderController(OrderService orderService, EtagService etagService) {
        this.orderService = orderService;
        this.etagService = etagService;
    }

    @Operation(summary = "Find order by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "304", description = "Not modified", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(
            @Parameter(description = "ID of the order to be retrieved") @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(summary = "Create a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created", content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict - Duplicate idempotency key", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
            })
    @PostMapping
    public ResponseEntity<OrderResponseDto> save(
            @Parameter(description = "Idempotency key to prevent duplicate requests, valid for 10 minutes.") @RequestHeader(name = "Idempotency-Key") UUID idempotencyKey,
            @Valid @RequestBody OrderRequestDto newOrder,
            BindingResult bindingResult) throws BadRequestException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("The provided data is incomplete or in an invalid format.", bindingResult);
        }
        OrderResponseDto responseDto = orderService.save(idempotencyKey, newOrder);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(etagService.generateEtag(responseDto))
                .body(responseDto);
    }

    @Operation(summary = "Find orders by criteria")
    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<OrderResponseDto>> findByCriteria(
            @Schema(hidden = true)
            @PageableDefault(
                    direction = Sort.Direction.ASC,
                    sort = {"orderNumber"},
                    size = 20
            ) Pageable pageable,
            @RequestParam(required = false, name = "orderNumber") @Parameter(description = "Order number to filter the results") String orderNumber,
            @RequestParam(required = false, name = "promoCode") @Parameter(description = "Promo code to filter the results") String promoCode,
            @RequestParam(required = false, name = "city") @Parameter(description = "City to filter the results") String city,
            @RequestParam(required = false, name = "street") @Parameter(description = "Street to filter the results") String street,
            @RequestParam(required = false, name = "zipCode") @Parameter(description = "Zip code to filter the results") String zipCode) {
        return ResponseEntity.ok(orderService.findByCriteria(
                pageable, promoCode, orderNumber, city, street, zipCode));
    }

    @Operation(summary = "Delete order by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order deleted successfully", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID of the order to be deleted") @PathVariable(name = "id") Long id) {
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Update order by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateById(
            @Parameter(description = "ID of the order to be updated") @PathVariable(name = "id") Long id,
            @RequestBody @Valid OrderRequestDto update,
            BindingResult bindingResult) throws BadRequestException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("The provided data is incomplete or in an invalid format.", bindingResult);
        }
        OrderResponseDto responseDto = orderService.updateById(id, update);
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(etagService.generateEtag(responseDto))
                .body(responseDto);
    }

    @Operation(summary = "Apply Patch to order by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order partially updated successfully", content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchById(
            @Parameter(description = "ID of the order to be modified") @PathVariable(name = "id") Long id,
            @Parameter(array = @ArraySchema(schema = @Schema(implementation = JsonPatchSchema.class)),
                    name = "JsonPatch",
                    description = "JSON Patch representation according to RFC 6902 for partial modification.")
            @RequestBody JsonNode patch) throws IOException {
        Object responseDto = orderService.patchById(id, patch);
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(etagService.generateEtag(responseDto))
                .body(responseDto);
    }
}
