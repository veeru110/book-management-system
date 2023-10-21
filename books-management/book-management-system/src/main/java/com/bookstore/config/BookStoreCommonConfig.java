package com.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.bookstore.constants.Constants.BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME;

@Configuration
@EnableAsync
public class BookStoreCommonConfig {

    @Bean(BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME)
    public ThreadPoolTaskExecutor bookStoreAsyncTasksThreadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(50);
        threadPoolTaskExecutor.setThreadNamePrefix("BookStoreAsyncTask-");
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskExecutor;
    }
}
