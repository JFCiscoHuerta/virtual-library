package com.gklyphon.VirtualLibrary.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the ObjectMapper used by Jackson.
 * This class registers the JavaTimeModule to handle Java 8 date and time types.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates and configures a custom ObjectMapper bean.
     * This ObjectMapper is configured to support Java 8 date and time types
     * through the JavaTimeModule.
     *
     * @return a configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
