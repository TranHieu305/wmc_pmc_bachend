package com.wms.wms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;

@Configuration
public class RedisConfig {
    @Value("${REDIS_URL}")
    private String redisUrl;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        try {
            URI redisUri = URI.create(redisUrl);

            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            redisConfig.setHostName(redisUri.getHost());
            redisConfig.setPort(redisUri.getPort());

            // Extract password if present
            if (redisUri.getUserInfo() != null) {
                String[] userInfo = redisUri.getUserInfo().split(":", 2);
                if (userInfo.length == 2) {
                    redisConfig.setPassword(userInfo[1]);
                }

                System.out.println("Connecting to Redis:");
                System.out.println("Host: " + redisUri.getHost());
                System.out.println("Port: " + redisUri.getPort());
                System.out.println("Password: " + (redisUri.getUserInfo() != null ? userInfo[1] : "No Password"));
            }


            // Configure SSL based on URI scheme
            LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfig = LettuceClientConfiguration.builder();
            if ("rediss".equals(redisUri.getScheme())) {
                clientConfig.useSsl().disablePeerVerification();; // Enable SSL for "rediss://" URLs
            }

            return new LettuceConnectionFactory(redisConfig, clientConfig.build());
        } catch (Exception e) {
            throw new RuntimeException("Invalid Redis URL: " + redisUrl, e);
        }

    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Use String serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        // Use JSON serializer for values
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
