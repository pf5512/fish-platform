package com.ippteam.fish.util.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pactera on 16/10/27.
 */

public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
    }
}