package dev.jl.jsonpatchspring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfiguration {
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter(){
        return new ShallowEtagHeaderFilter();
    }
}
