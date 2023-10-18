package com.bookstore.constants;

public interface Constants {

    String UNKNOWN_ERROR = "Unknown Error";
    String AUTHORIZATION = "Authorization";

    String BEARER = "Bearer";
    String[] UNAUTHENTICATED_CONTEXT_PATHS = new String[]{"/users/login", "/users/login1", "/users/register", "/dashboard"};

    String USER_MESSAGE_GENRES_NOT_PRESENT = "Following Genres not present: %s, but you can will be notified as they arrive";
}

