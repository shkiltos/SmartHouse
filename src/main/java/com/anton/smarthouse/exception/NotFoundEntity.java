package com.anton.smarthouse.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundEntity extends RuntimeException {
    public NotFoundEntity(String message) {
        super(message);
        log.error(message);
    }
}
