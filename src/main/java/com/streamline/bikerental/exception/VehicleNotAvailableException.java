package com.streamline.bikerental.exception;

public class VehicleNotAvailableException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public VehicleNotAvailableException(String message) {
        super(message);
    }
}
