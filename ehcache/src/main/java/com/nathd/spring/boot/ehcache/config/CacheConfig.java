package com.nathd.spring.boot.ehcache.config;

import com.nathd.spring.boot.ehcache.domain.KafkaEvent;
import org.ehcache.CacheManager;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;

@Configuration
public class CacheConfig {
    public static final String CACHE_EVENTS = "events";

    @Bean
    public CacheManager cacheManager() {
        return newCacheManagerBuilder()
                .withCache("events", newCacheConfigurationBuilder(String.class, KafkaEvent.class, heap(100))
                        .withExpiry(ExpiryPolicy.NO_EXPIRY)
                )
                .build(true);
    }
}
