package com.receipe.manager.exception;

import java.io.Serial;

public class RecordsNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RecordsNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
