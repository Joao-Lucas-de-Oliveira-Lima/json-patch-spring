package dev.jl.jsonpatchspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class JsonPatchSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonPatchSpringApplication.class, args);
    }

}
