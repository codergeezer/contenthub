package com.codergeezer.contenthub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author haidv
 * @version 1.0
 */
@Configuration
public class TaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor eventHandle() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("EventHandle-");
        executor.initialize();
        return executor;
    }
}
