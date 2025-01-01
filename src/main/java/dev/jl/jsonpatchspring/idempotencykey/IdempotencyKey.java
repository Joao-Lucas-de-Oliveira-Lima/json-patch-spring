package dev.jl.jsonpatchspring.idempotencykey;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RedisHash("idempotencyKey")
public class IdempotencyKey {
    @Id
    private UUID key;
}
