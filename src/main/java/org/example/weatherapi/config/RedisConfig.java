package org.example.weatherapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final CacheManager cacheManager;

    @EventListener(ContextClosedEvent.class)
    public void onApplicationEvent() {
        cacheManager.getCacheNames().parallelStream()
                .forEach(cache -> Optional.ofNullable(cacheManager.getCache(cache))
                            .ifPresent(Cache::clear)
                );
    }

}
