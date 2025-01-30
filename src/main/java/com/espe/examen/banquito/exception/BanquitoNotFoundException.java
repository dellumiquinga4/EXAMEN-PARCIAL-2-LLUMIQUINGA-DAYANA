package com.espe.examen.banquito.exception;

public class BanquitoNotFoundException extends BanquitoException {
    public BanquitoNotFoundException(String message) {
        super(message);
    }

    public BanquitoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 