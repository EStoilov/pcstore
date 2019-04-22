package com.softuni.pcstore.error;

import com.softuni.pcstore.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = Constants.EXCEPTION_NOT_FOUND)
public class NotFoundExceptions extends RuntimeException {
    public NotFoundExceptions() {
    }

    public NotFoundExceptions(String message) {
        super(message);
    }
}
