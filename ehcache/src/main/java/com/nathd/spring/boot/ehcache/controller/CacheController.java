package com.nathd.spring.boot.ehcache.controller;

import com.nathd.spring.boot.ehcache.domain.KafkaEvent;
import com.nathd.spring.boot.ehcache.service.CacheOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CacheController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final CacheOperation cacheOperation;

    public CacheController(CacheOperation cacheOperation) {
        this.cacheOperation = cacheOperation;
    }

    @GetMapping("/unprocessed")
    public List<KafkaEvent> unprocessed() {
        return cacheOperation.getAllUnprocessed();
    }

    @PostMapping("/unprocessed/{topic}/{partition}/{offset}")
    public KafkaEvent addUnprocessed(
            @PathVariable("topic") String topic, @PathVariable("partition") Integer partition, @PathVariable("offset") Long offset) {
        return cacheOperation.addUnprocessed(topic, partition, offset);
    }

    @DeleteMapping("/unprocessed/{topic}/{partition}/{offset}")
    public KafkaEvent removeUnprocessed(
            @PathVariable("topic") String topic, @PathVariable("partition") Integer partition, @PathVariable("offset") Long offset) {
        return cacheOperation.removeUnprocessed(topic, partition, offset);
    }
}
