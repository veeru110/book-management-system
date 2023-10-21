package com.bookstore.controller.advice;

import com.bookstore.exception.DuplicateMembershipException;
import com.bookstore.exception.MembershipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BookStoreGlobalExceptionHandler {

    private static final String ERROR_MESSAGE = "error";
    private static final String STATUS = "status";
    private static final String FAILURE = "failure";

    private Map<String, String> doReturnErrorMap(Exception exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, exception.getMessage());
        errorMap.put(STATUS, FAILURE);
        return errorMap;
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUserNotFound(UsernameNotFoundException exception) {
        return doReturnErrorMap(exception);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateMembershipException.class)
    public Map<String, String> handleDuplicateMembership(DuplicateMembershipException exception) {
        return doReturnErrorMap(exception);
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(MembershipNotFoundException.class)
    public Map<String, String> handleMembershipNotFound(MembershipNotFoundException exception) {
        return doReturnErrorMap(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleInternalServerError(Exception exception) {
        return doReturnErrorMap(exception);
    }

}
