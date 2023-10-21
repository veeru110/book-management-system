package com.bookstore.constants;

public interface Constants {

    String UNKNOWN_ERROR = "Unknown Error";
    String AUTHORIZATION = "Authorization";

    String BEARER = "Bearer";
    String[] UNAUTHENTICATED_CONTEXT_PATHS = new String[]{"/users/login", "/users/register", "/", "/v3/api-docs/**", "/swagger-ui/**", "/v2/api-docs/**", "/swagger-resources/**", "/v3/api-docs.yaml", "/api-docs.yaml"};

    String USER_MESSAGE_GENRES_NOT_PRESENT = "Following Genres not present: %s, but you can will be notified as they arrive";

    String BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME = "bookStoreAsyncTasks";

}

