package com.nathd.spring.cache.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class KafkaEvent {
    private final String topic;
    private final int partition;
    private final long offset;
}
