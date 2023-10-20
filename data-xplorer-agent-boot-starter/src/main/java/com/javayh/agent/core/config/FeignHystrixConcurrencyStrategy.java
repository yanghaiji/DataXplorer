//package com.javayh.agent.core.config;
//
//import com.netflix.hystrix.strategy.HystrixPlugins;
//import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
//import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
//import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
//import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
//import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//
//import java.util.concurrent.Callable;
//
//
///**
// * <p>
// *     https://codeantenna.com/a/AR1TRWHICj
// * </p>
// * @author haiji
// */
//public class FeignHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {
//
//    private static final Logger log = LoggerFactory.getLogger(FeignHystrixConcurrencyStrategy.class);
//
//    private HystrixConcurrencyStrategy delegate;
//
//    public FeignHystrixConcurrencyStrategy() {
//        try {
//            this.delegate = HystrixPlugins.getInstance().getConcurrencyStrategy();
//            if (this.delegate instanceof FeignHystrixConcurrencyStrategy) {
//                // Welcome to singleton hell...
//                return;
//            }
//
//            HystrixCommandExecutionHook commandExecutionHook =
//                    HystrixPlugins.getInstance().getCommandExecutionHook();
//
//            HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
//            HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
//            HystrixPropertiesStrategy propertiesStrategy =
//                    HystrixPlugins.getInstance().getPropertiesStrategy();
//            this.logCurrentStateOfHystrixPlugins(eventNotifier, metricsPublisher, propertiesStrategy);
//
//            HystrixPlugins.reset();
//            HystrixPlugins instance = HystrixPlugins.getInstance();
//            instance.registerConcurrencyStrategy(this);
//            instance.registerCommandExecutionHook(commandExecutionHook);
//            instance.registerEventNotifier(eventNotifier);
//            instance.registerMetricsPublisher(metricsPublisher);
//            instance.registerPropertiesStrategy(propertiesStrategy);
//        } catch (Exception e) {
//            log.error("Failed to register Sleuth Hystrix Concurrency Strategy", e);
//        }
//    }
//
//    private void logCurrentStateOfHystrixPlugins(HystrixEventNotifier eventNotifier,
//                                                 HystrixMetricsPublisher metricsPublisher,
//                                                 HystrixPropertiesStrategy propertiesStrategy) {
//        if (log.isDebugEnabled()) {
//            log.debug("Current Hystrix plugins configuration is [" + "concurrencyStrategy ["
//                    + this.delegate + "]," + "eventNotifier [" + eventNotifier + "]," + "metricPublisher ["
//                    + metricsPublisher + "]," + "propertiesStrategy [" + propertiesStrategy + "]," + "]");
//            log.debug("Registering Sleuth Hystrix Concurrency Strategy.");
//        }
//    }
//
//    @Override
//    public <T> Callable<T> wrapCallable(Callable<T> callable) {
//        return new RequestAttributeAwareCallable<>(callable, RequestContextHolder.getRequestAttributes());
//    }
//
//    static class RequestAttributeAwareCallable<T> implements Callable<T> {
//
//        private final Callable<T> delegate;
//        private final RequestAttributes requestAttributes;
//
//        public RequestAttributeAwareCallable(Callable<T> callable, RequestAttributes requestAttributes) {
//            this.delegate = callable;
//            this.requestAttributes = requestAttributes;
//        }
//
//        @Override
//        public T call() throws Exception {
//            try {
//                RequestContextHolder.setRequestAttributes(requestAttributes);
//                return delegate.call();
//            } finally {
//                RequestContextHolder.resetRequestAttributes();
//            }
//        }
//    }
//}