package com.vehicleDataApp.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VehicleDataConstants {
    public static final Map<String, String> PREFIX_TO_VEHICLE_TYPE;

    public static final String VEHICLE_DATA = "vehicleData";
    //types of vehicles
    public static final String TRACTOR = "tractor";
    public static final String COMBINE = "combine";

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("LD_A", TRACTOR);
        aMap.put("LD_C", COMBINE);
        // Add more mappings as needed
        PREFIX_TO_VEHICLE_TYPE = Collections.unmodifiableMap(aMap);
    }
}
