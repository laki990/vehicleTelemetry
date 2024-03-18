package com.vehicleDataApp.exceptions;

import lombok.Value;

@Value
public class ErrorResponse {

    int errorCode;
    String errorMessage;

}
