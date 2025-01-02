package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.exception.IdempotencyKeyConflictException;
import dev.jl.jsonpatchspring.exception.ResourceNotFoundException;
import dev.jl.jsonpatchspring.idempotencykey.IdempotencyKey;
import dev.jl.jsonpatchspring.idempotencykey.IdempotencyKeyService;
import dev.jl.jsonpatchspring.utils.mapper.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final IdempotencyKeyService idempotencyKeyService;
    private final Mapper mapper;

    public OrderService(OrderRepository orderRepository, IdempotencyKeyService idempotencyKeyService, Mapper mapper) {
        this.orderRepository = orderRepository;
        this.idempotencyKeyService = idempotencyKeyService;
        this.mapper = mapper;
    }

    @Cacheable(value = "order", key = "#id")
    public OrderResponseDto findById(Long id) throws ResourceNotFoundException {
        Order orderFound = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        return mapper.mapToObject(orderFound, OrderResponseDto.class);
    }

    @CachePut(value = "order", key = "#result.id")
    @Transactional
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
}
