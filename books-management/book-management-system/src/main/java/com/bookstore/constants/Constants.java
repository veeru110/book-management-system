package com.bookstore.constants;

public interface Constants {

    String UNKNOWN_ERROR = "Unknown Error";
    String AUTHORIZATION = "Authorization";

    String BEARER = "Bearer";
    String[] UNAUTHENTICATED_CONTEXT_PATHS = new String[]{"/users/login", "/users/register", "/dashboard"};
}

