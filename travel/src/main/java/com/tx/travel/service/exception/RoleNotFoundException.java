package com.tx.travel.service.exception;

import com.tx.travel.commons.oauth.security.ERole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Value not present")
public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(final ERole role) {
        super(MessageFormat.format("Error: Role {0} does not exist!", role));
    }
}

