package com.vehicleDataApp.controller;

import com.vehicleDataApp.dto.VehicleFilter;
import com.vehicleDataApp.exceptions.ErrorResponse;
import com.vehicleDataApp.exceptions.VehicleDataException;
import com.vehicleDataApp.service.VehicleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class VehicleDataController {

    @Autowired
    private VehicleDataService vehicleDataService;

    @PostMapping("/vehicles/filter")
    public List<Map> filterVehicleData(@RequestBody List<VehicleFilter> criteriaList) {
        return vehicleDataService.findVehicleDataByCriteria(criteriaList);
    }

    @ExceptionHandler(VehicleDataException.class)
    public ResponseEntity<ErrorResponse> handleFilteringException(VehicleDataException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}
