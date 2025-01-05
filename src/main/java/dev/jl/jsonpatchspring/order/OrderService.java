package dev.jl.jsonpatchspring.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dev.jl.jsonpatchspring.exception.IdempotencyKeyConflictException;
import dev.jl.jsonpatchspring.exception.ResourceNotFoundException;
import dev.jl.jsonpatchspring.idempotencykey.IdempotencyKeyService;
import dev.jl.jsonpatchspring.utils.mapper.Mapper;
import dev.jl.jsonpatchspring.utils.patch.PatchValidator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final IdempotencyKeyService idempotencyKeyService;
    private final Mapper mapper;
    private final PatchValidator patchValidator;

    public OrderService(OrderRepository orderRepository,
                        IdempotencyKeyService idempotencyKeyService,
                        PatchValidator patchValidator,
                        Mapper mapper) {
        this.orderRepository = orderRepository;
        this.idempotencyKeyService = idempotencyKeyService;
        this.mapper = mapper;
        this.patchValidator = patchValidator;
    }

    @Cacheable(value = "order", key = "#id")
    public OrderResponseDto findById(Long id) throws ResourceNotFoundException {
        Order orderFound = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with ID %d not found.", id)));
        return mapper.mapToObject(orderFound, OrderResponseDto.class);
    }

    @Transactional
    @CachePut(value = "order", key = "#result.id")
    @CacheEvict(value = "orderPage", allEntries = true)
    public OrderResponseDto save(UUID idempotencyKey, OrderRequestDto newOrder) throws IdempotencyKeyConflictException {
        if (idempotencyKeyService.findById(idempotencyKey)) {
            String message = String.format(
                    "The idempotency key '%s' has already been used. Please wait for 10 minutes before using it again.",
                    idempotencyKey.toString()
            );
            throw new IdempotencyKeyConflictException(message);
        }
        Order orderSaved = orderRepository.save(mapper.mapToObject(newOrder, Order.class));
        idempotencyKeyService.save(idempotencyKey);
        return mapper.mapToObject(orderSaved, OrderResponseDto.class);
    }

    @Cacheable(value = "orderPage")
    public Page<OrderResponseDto> findByCriteria(
            Pageable pageable, String promoCode, String orderNumber, String city, String street, String zipCode) {
        Page<Order> orderPage = orderRepository.findByCriteria(
                pageable, orderNumber, promoCode, city, street, zipCode);
        return orderPage.map(order -> mapper.mapToObject(order, OrderResponseDto.class));
    }

    @Caching(evict = {
            @CacheEvict(value = "order", key = "#id"),
            @CacheEvict(value = "orderPage", allEntries = true)
    })
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    @CachePut(value = "order", key = "#id")
    @CacheEvict(value = "order", allEntries = true)
    public OrderResponseDto updateById(Long id, OrderRequestDto updateData) throws ResourceNotFoundException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with ID %d not found.", id)));
        mapper.mapProperties(updateData, existingOrder);
        Order savedOrder = orderRepository.save(existingOrder);
        return mapper.mapToObject(savedOrder, OrderResponseDto.class);
    }


    @Transactional
    @CachePut(value = "order", key = "#result.id")
    @CacheEvict(value = "orderPage", allEntries = true)
    public Object patchById(Long id, JsonNode patch) throws ResourceNotFoundException, JsonProcessingException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with ID %d not found.", id)));

        OrderRequestDto existingOrderWithConstraintValidations = mapper.mapToObject(existingOrder, OrderRequestDto.class);
        OrderRequestDto patchedOrder =
                patchValidator.validate(existingOrderWithConstraintValidations, OrderRequestDto.class, patch);

        mapper.mapProperties(patchedOrder, existingOrder);
        Order orderSaved = orderRepository.save(existingOrder);
        return mapper.mapToObject(orderSaved, OrderResponseDto.class);
    }

}
