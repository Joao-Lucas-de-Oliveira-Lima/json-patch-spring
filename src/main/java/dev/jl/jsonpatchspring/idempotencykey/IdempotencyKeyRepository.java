package dev.jl.jsonpatchspring.idempotencykey;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IdempotencyKeyRepository extends CrudRepository<IdempotencyKey, UUID> {
}
