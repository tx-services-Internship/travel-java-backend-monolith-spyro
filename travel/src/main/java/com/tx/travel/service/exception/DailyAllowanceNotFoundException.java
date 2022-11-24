package com.tx.travel.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found")
public class DailyAllowanceNotFoundException extends RuntimeException{

    public DailyAllowanceNotFoundException(final Long id) {
        super(MessageFormat.format("Error: {0} not found!", id));
    }
    public DailyAllowanceNotFoundException(final String region) {
        super(MessageFormat.format("Error: {0} not found!", region));
    }
}
