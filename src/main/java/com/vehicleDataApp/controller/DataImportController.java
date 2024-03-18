package com.vehicleDataApp.controller;

import com.vehicleDataApp.exceptions.ErrorResponse;
import com.vehicleDataApp.exceptions.VehicleDataException;
import com.vehicleDataApp.service.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@ControllerAdvice
public class DataImportController {

    @Autowired
    private CsvImportService csvImportService;

    @PostMapping("/vehicles/importCsv")
    public ResponseEntity<String> importCsv(MultipartFile file) {
            csvImportService.importCsv(file);
            return ResponseEntity.ok("Csv file imported sucessfully");

    }

    @ExceptionHandler(VehicleDataException.class)
    public ResponseEntity<ErrorResponse> handleFilteringException(VehicleDataException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}
