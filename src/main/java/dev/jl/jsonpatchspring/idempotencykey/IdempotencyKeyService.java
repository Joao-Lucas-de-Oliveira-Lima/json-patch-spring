package dev.jl.jsonpatchspring.idempotencykey;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdempotencyKeyService {
    private final IdempotencyKeyRepository idempotencyKeyRepository;

    public IdempotencyKeyService(IdempotencyKeyRepository idempotencyKeyRepository) {
        this.idempotencyKeyRepository = idempotencyKeyRepository;
    }

    public Boolean findById(UUID key) {
        return idempotencyKeyRepository.findById(key).isPresent();
    }

    public void save(UUID key) {
        idempotencyKeyRepository.save(new IdempotencyKey(key));
    }
}
