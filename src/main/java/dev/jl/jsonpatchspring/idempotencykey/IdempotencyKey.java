package dev.jl.jsonpatchspring.idempotencykey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "idempotencyKey", timeToLive = 600 /* 10 minutes */)
public class IdempotencyKey {
    @Id
    private UUID key;
}
