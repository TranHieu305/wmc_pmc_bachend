package com.wms.wms.service.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedisCacheService {
    public static final String ALL_USERS_KEY = "USERS";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final String WMS_CACHE_PREFIX = "WMS:";
    private static final long DEFAULT_EXPIRE_TIME_IN_MINUTES = 60;


    /**
     * Cache an object with a time-to-live (TTL).
     *
     * @param key     the key under which the object will be stored
     * @param value   the object to cache
     * @param ttl     the time-to-live (TTL) in seconds
     */
    public void setCachedObjectWithExpired(String key, Object value, long ttl) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(WMS_CACHE_PREFIX + key, jsonValue, ttl, TimeUnit.SECONDS);
            log.info(String.format("Cached object with key: %s", key));
        } catch (Exception e) {
            log.error(String.format("Failed to cache object with key %s: %s", key, e.getMessage()));
        }
    }

    /**
     * Cache an object with a time-to-live equals DEFAULT_EXPIRE_TIME_IN_MINUTES (TTL).
     *
     * @param key     the key under which the object will be stored
     * @param value   the object to cache
     */
    public void setCachedObjectWithDefaultExpired(String key, Object value) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(WMS_CACHE_PREFIX + key, jsonValue, DEFAULT_EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            log.info(String.format("Cached object with key: %s", key));
        } catch (Exception e) {
            log.error(String.format("Failed to cache object with key %s: %s", key, e.getMessage()));
        }
    }

    /**
     * Cache an object permanently.
     *
     * @param key   the key under which the object will be stored
     * @param value the object to cache
     */
    public void setPermanentCache(String key, Object value) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(WMS_CACHE_PREFIX + key, jsonValue);
            log.info(String.format("Permanently cached object with key: %s", key));
        } catch (Exception e) {
            log.error(String.format("Failed to permanently cache object with key %s: %s", key, e.getMessage()));
        }
    }

    /**
     * Retrieve a cached object from Redis and convert it to the specified type.
     *
     * @param key   the key of the cached object
     * @param clazz the class of the object to be returned
     * @param <T>   the type of the object
     * @return the cached object, or null if not found or an error occurs
     */
    public <T> T getCachedObject(String key, Class<T> clazz) {
        try {
            String jsonValue = (String) redisTemplate.opsForValue().get(WMS_CACHE_PREFIX + key);
            if (jsonValue != null) {
                return objectMapper.readValue(jsonValue, clazz);
            } else {
                log.info(String.format("Cache miss for key: %s", key));
            }
        } catch (Exception e) {
            log.warn(String.format("Failed to retrieve cache for key %s: %s", key, e.getMessage()));
        }
        return null;
    }

    public <T> List<T> getCachedListObject(String key, Class<T> clazz) {
        try {
            // Retrieve the JSON string from the cache
            String jsonValue = (String) redisTemplate.opsForValue().get(WMS_CACHE_PREFIX + key);

            if (jsonValue != null) {
                // Construct a collection type for deserialization
                JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);

                // Deserialize the JSON string into a list of objects
                return objectMapper.readValue(jsonValue, type);
            } else {
                log.info(String.format("Cache miss for key: %s", key));
            }
        } catch (Exception e) {
            log.warn(String.format("Failed to retrieve list from cache for key %s: %s", key, e.getMessage()));
        }

        return null;
    }

    /**
     * Remove an object from the cache.
     *
     * @param key the key of the cached object to remove
     */
    public void removeCachedObject(String key) {
        try {
            redisTemplate.delete(WMS_CACHE_PREFIX + key);
            log.info(String.format("Removed cached object with key: %s", key));
        } catch (Exception e) {
            log.error(String.format("Failed to remove cached object with key %s: %s", key, e.getMessage()));
        }
    }
}
