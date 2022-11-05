//package com.project.ddd.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//@Configuration
//@EnableAsync
//public class AsyncConfiguration {
//
//    @Bean("couponAsync")
//    public TaskExecutor myTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setMaxPoolSize(15);
//        executor.setCorePoolSize(10);
//        executor.setQueueCapacity(Integer.MAX_VALUE);
//        executor.setThreadNamePrefix("couponAsync-");
//        executor.initialize();
//        return executor;
//    }
//}