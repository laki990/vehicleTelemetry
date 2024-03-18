package com.vehicleDataApp.dto;

import lombok.Getter;

@Getter
public class VehicleFilter {

    private String field;

    private Object value;

    private String operation;
}
