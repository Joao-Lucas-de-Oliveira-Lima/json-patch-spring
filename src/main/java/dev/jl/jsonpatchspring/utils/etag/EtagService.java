package dev.jl.jsonpatchspring.utils.etag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class EtagService extends ShallowEtagHeaderFilter {

    private final ObjectMapper objectMapper;

    public EtagService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String generateEtag(T object) throws IOException {
        String objectAsJsonString = objectMapper.writeValueAsString(object);

        try (InputStream inputStream = new ByteArrayInputStream(objectAsJsonString.getBytes())) {
            return generateETagHeaderValue(inputStream, false);
        }
    }
}

