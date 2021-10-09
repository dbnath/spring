package com.nathd.spring.cache.service;

import com.nathd.spring.cache.domain.KafkaEvent;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.nathd.spring.cache.config.SimpleCacheCustomizer.CACHE_EVENTS;

@Service
@CacheConfig(cacheNames = {CACHE_EVENTS})
public class CacheOperation {

    private CacheManager cacheManager;
    private CacheProperties cacheProperties;

    public CacheOperation(CacheManager cacheManager, CacheProperties cacheProperties) {
        this.cacheManager = cacheManager;
        this.cacheProperties = cacheProperties;
    }

    @SuppressWarnings("unchecked")
    public List<KafkaEvent> getAllUnprocessed() {
        ConcurrentHashMap<String, KafkaEvent> nativeCache = (ConcurrentHashMap<String, KafkaEvent>) cacheManager
                .getCache(CACHE_EVENTS).getNativeCache();
        return new LinkedList<>(nativeCache.values());
    }

    @CachePut
    public KafkaEvent addUnprocessed(String topic, int partition, long offset) {
        return new KafkaEvent(topic, partition, offset);
    }

    @CacheEvict
    public KafkaEvent removeUnprocessed(String topic, int partition, long offset) {
        return new KafkaEvent(topic, partition, offset);
    }
}
