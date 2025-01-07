package dev.jl.jsonpatchspring.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("JsonPatch Spring API")
                        .version("1.0.0-SNAPSHOT")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                );
    }
}
