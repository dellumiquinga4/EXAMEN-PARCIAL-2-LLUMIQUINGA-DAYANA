package com.espe.examen.banquito.exception;

public class BanquitoValidationException extends BanquitoException {
    public BanquitoValidationException(String message) {
        super(message);
    }

    public BanquitoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
} 