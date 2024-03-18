package com.vehicleDataApp.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    GENERAL_ERROR(1001, "Something went wrong while processing your request."),
    UNSUPPORTED_OPERATION(1002, "The requested operation is not supported for field you provided."),
    UNSUPPORTED_FILENAME(1003, "Filename you have provided is not in proper format"),
    CSV_IMPORT_ERROR(1004, "There was one error while trying to import csv file.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}