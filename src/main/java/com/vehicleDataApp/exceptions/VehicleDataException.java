package com.vehicleDataApp.exceptions;

import lombok.Getter;

@Getter
public class VehicleDataException extends RuntimeException {
    private final int errorCode;

    public VehicleDataException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
