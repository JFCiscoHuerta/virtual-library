package com.gklyphon.VirtualLibrary.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class maps the Redis configuration properties from the application
 * configuration file (application.properties or application.yml).
 * It uses the 'spring.redis' prefix to bind the configuration values.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 19-Oct-2024
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "redis.connection")
public class RedisData {

    /**
     * The port used to connect to the Redis server.
     */
    private int port;

    /**
     * The host address of the Redis server.
     */
    private String host;
}
