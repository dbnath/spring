package com.nathd.spring.boot.ehcache.service;

import com.nathd.spring.boot.ehcache.domain.KafkaEvent;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.stereotype.Service;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import java.util.LinkedList;
import java.util.List;

import static com.nathd.spring.boot.ehcache.config.CacheConfig.CACHE_EVENTS;


@Service
@CacheDefaults(cacheName = CACHE_EVENTS)
public class CacheOperation {

    private final CacheManager cacheManager;

    public CacheOperation(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @SuppressWarnings("unchecked")
    public List<KafkaEvent> getAllUnprocessed() {
        List<KafkaEvent> unprocessed = new LinkedList<>();
        for (Cache.Entry<String, KafkaEvent> stringKafkaEventEntry : cacheManager.getCache(CACHE_EVENTS, String.class, KafkaEvent.class)) {
            unprocessed.add(stringKafkaEventEntry.getValue());
        }
        return unprocessed;
    }

    @CachePut(cacheName = CACHE_EVENTS)
    public KafkaEvent addUnprocessed(String topic, int partition, long offset) {
        Cache<String, KafkaEvent> cache = cacheManager.getCache(CACHE_EVENTS, String.class, KafkaEvent.class);
        cache.put(topic.concat("-").concat(partition+"").concat("-").concat(offset+""), new KafkaEvent(topic, partition, offset));
        return new KafkaEvent(topic, partition, offset);
    }

    @CacheRemove(cacheName = CACHE_EVENTS)
    public KafkaEvent removeUnprocessed(String topic, int partition, long offset) {
        Cache<String, KafkaEvent> cache = cacheManager.getCache(CACHE_EVENTS, String.class, KafkaEvent.class);
        cache.remove(topic.concat("-").concat(partition+"").concat("-").concat(offset+""));
        return new KafkaEvent(topic, partition, offset);
    }
}
