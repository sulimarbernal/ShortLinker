package com.ShortLinker.application.metrics;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MetricsAspect {

    @Autowired
    private MetricsInterface metrics;

    @Before("@annotation(metricAnnotation)")
    public void beforeMethod(Metric metricAnnotation) {
        metrics.incrementReceive(metricAnnotation.value());
    }

    @AfterReturning(pointcut = "@annotation(metricAnnotation)", returning = "response")
    public void afterReturning(Metric metricAnnotation, Object response) {
        metrics.incrementSuccessful(metricAnnotation.value());
    }

    @AfterThrowing(pointcut = "@annotation(metricAnnotation)", throwing = "ex")
    public void afterThrowing(Metric metricAnnotation, Exception ex) {
        metrics.incrementError(metricAnnotation.value());
    }
}
