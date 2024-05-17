package com.ShortLinker.application.metrics;

public interface MetricsInterface {
    void incrementReceive(String serviceName);
    void incrementSuccessful(String serviceName);
    void incrementError(String serviceName);
}

