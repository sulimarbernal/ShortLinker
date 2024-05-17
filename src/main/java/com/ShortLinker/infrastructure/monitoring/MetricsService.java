package com.ShortLinker.infrastructure.monitoring;

import com.ShortLinker.application.metrics.MetricsInterface;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class MetricsService implements MetricsInterface {
    private final MeterRegistry registry;
    private final ConcurrentMap<String, Counter> receiveCounters = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Counter> successfulCounters = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Counter> errorCounters = new ConcurrentHashMap<>();

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    private Counter getOrCreateCounter(ConcurrentMap<String, Counter> counterMap, String name, String serviceName) {
        return counterMap.computeIfAbsent(serviceName,
                s -> Counter.builder(name)
                        .tag("service", serviceName)
                        .register(registry));
    }

    @Override
    public void incrementReceive(String serviceName) {
        Counter counter = getOrCreateCounter(receiveCounters, "request_receive", serviceName);
        counter.increment();
    }

    @Override
    public void incrementSuccessful(String serviceName) {
        Counter counter = getOrCreateCounter(successfulCounters, "request_successful", serviceName);
        counter.increment();
    }

    @Override
    public void incrementError(String serviceName) {
        Counter counter = getOrCreateCounter(errorCounters, "request_error", serviceName);
        counter.increment();
    }
}
