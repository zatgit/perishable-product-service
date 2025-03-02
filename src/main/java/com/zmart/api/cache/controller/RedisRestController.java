package com.zmart.api.cache.controller;

import com.zmart.api.cache.CacheRefreshCronJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.zmart.api.docs.ApiDocsConstants.API_BASE_RESOURCE_PATH;
import static com.zmart.api.docs.cache.SwaggerCacheExampleConstants.CACHE_RESP_KEY_NOT_FOUND_EXAMPLE;
import static com.zmart.api.docs.cache.SwaggerCacheExampleConstants.CREATE_AND_GET_CACHE_KVP_REQ_AND_RESP_SUCCESS_EXAMPLE;
import static com.zmart.api.docs.cache.SwaggerCacheExampleConstants.EVICT_CACHE_RESP_SUCCESS_EXAMPLE;
import static com.zmart.api.docs.cache.SwaggerCacheExampleConstants.GET_ALL_CACHE_KEYS_RESP_NOT_FOUND_EXAMPLE;
import static com.zmart.api.docs.cache.SwaggerCacheExampleConstants.GET_ALL_CACHE_KEYS_RESP_SUCCESS_EXAMPLE;
import static com.zmart.api.product.util.ProductConstants.EMPTY_STRING;

@CrossOrigin(origins = {"${server.url.local}", "${server.url.production}"})
@RestController
@RequestMapping(
        path = API_BASE_RESOURCE_PATH + "redis",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cache", description = "Cache management APIs")
public class RedisRestController {

    private final RedisTemplate<String, String> template;
    private CacheRefreshCronJob cacheRefreshCronJob;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RedisRestController(
            final RedisTemplate<String, String> template,
            final CacheRefreshCronJob cacheRefreshCronJob) {
        this.template = template;
        this.cacheRefreshCronJob = cacheRefreshCronJob;
    }

    @PostMapping("create")
    @Operation(summary = "Create new Redis key-value pair")
    @ApiResponse(responseCode = "201", description = "Key-value pair created successfully",
            content = {@Content(examples = { @ExampleObject(CREATE_AND_GET_CACHE_KVP_REQ_AND_RESP_SUCCESS_EXAMPLE)})})
    @ResponseStatus(HttpStatus.CREATED)
    public Map.Entry<String, String> createRedisKeyValuePair(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = @ExampleObject(CREATE_AND_GET_CACHE_KVP_REQ_AND_RESP_SUCCESS_EXAMPLE))})
            @RequestBody final Map.Entry<String, String> kvp) {
        template.opsForValue().set(kvp.getKey(), kvp.getValue());
        return kvp;
    }

    @GetMapping("keys/{key}")
    @Operation(summary = "Get Redis key-value pair by key")
    @ApiResponse(responseCode = "200", description = "Key-value pair fetched successfully",
            content = {@Content(examples = {@ExampleObject(CREATE_AND_GET_CACHE_KVP_REQ_AND_RESP_SUCCESS_EXAMPLE)})})
    @ApiResponse(responseCode = "404", description = "Key not found",
            content = {@Content(examples = {@ExampleObject(CACHE_RESP_KEY_NOT_FOUND_EXAMPLE)})})
    public ResponseEntity<Map.Entry<String, String>> getRedisKeyValuePairByKey(
            @Parameter(description = "Key to lookup") @PathVariable("key") final String key) {
        final String value = Optional.ofNullable(
                template.opsForValue().get(key)).orElse(EMPTY_STRING);
        final Map.Entry<String, String> kvp = new AbstractMap.SimpleEntry<>(key, value);
        return new ResponseEntity<>(kvp.getValue().isEmpty() ? Map.entry("message", "Key not found") : kvp,
                kvp.getValue().isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("keys/all")
    @Operation(summary = "Get all Redis key-value pairs")
    @ApiResponse(responseCode = "200", description = "All key-value pairs fetched successfully",
            content = {@Content(examples = @ExampleObject(GET_ALL_CACHE_KEYS_RESP_SUCCESS_EXAMPLE))})
    @ApiResponse(responseCode = "200", description = "Keys not found",
            content = {@Content(examples = @ExampleObject(GET_ALL_CACHE_KEYS_RESP_NOT_FOUND_EXAMPLE))})
    public ResponseEntity<Map<String, Set<String>>> getAllRedisKeys() {
        template.setKeySerializer(new StringRedisSerializer());
        final Set<String> keySet = Optional.ofNullable(template.keys("*")).orElse(Set.of());
        return ResponseEntity.ok(Map.of("keys", keySet));
    }

    @GetMapping("evict")
    @Operation(summary = "Refresh the cache")
    @ApiResponse(responseCode = "200", description = "Cache refreshed successfully",
            content = {@Content(examples = @ExampleObject(EVICT_CACHE_RESP_SUCCESS_EXAMPLE))})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> evictCache() {
        cacheRefreshCronJob.evictCache();
        return Map.of("message", "Cache refreshed successfully");
    }
}
