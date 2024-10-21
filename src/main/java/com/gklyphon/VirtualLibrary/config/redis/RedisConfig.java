package com.gklyphon.VirtualLibrary.config.redis;

import io.lettuce.core.RedisConnectionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis configuration class that uses Lettuce as the Redis client.
 * It defines the necessary beans for Redis connections and operations.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 19-Oct-2024
 */
@Configuration
public class RedisConfig {

    private final RedisData redisData;

    public RedisConfig(RedisData redisData) {
        this.redisData = redisData;
    }

    /**
     * Creates and configures a {@link LettuceConnectionFactory}
     * using Redis in standalone mode.
     *
     * @return A connection factory for Redis.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        try {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(
                    redisData.getHost(), redisData.getPort());
            return new LettuceConnectionFactory(config);
        } catch (Exception ex) {
            throw new RedisConnectionException("Failed to connect to Redis server", ex);
        }
    }

    /**
     * Creates and configures a {@link RedisTemplate} for Redis operations.
     * Sets custom serializers for keys and values.
     *
     * @param redisConnectionFactory The Redis connection factory.
     * @return A configured RedisTemplate instance.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

}
