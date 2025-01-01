package dev.jl.jsonpatchspring.order;

import dev.jl.jsonpatchspring.exception.ResourceNotFoundException;
import dev.jl.jsonpatchspring.utils.mapper.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final Mapper mapper;

    public OrderService(OrderRepository orderRepository, Mapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Cacheable(value = "order", key = "#id")
    public OrderResponseDto findById(Long id) throws ResourceNotFoundException{
        Order orderFound = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        return mapper.mapToObject(orderFound, OrderResponseDto.class);
    }
}
