package com.vehicleDataApp.service;

import com.vehicleDataApp.exceptions.ErrorCode;
import com.vehicleDataApp.exceptions.VehicleDataException;
import com.vehicleDataApp.repository.VehicleDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvImportService {

    @Autowired
    private VehicleDataRepository vehicleDataRepository;
    private static final int BATCH_SIZE = 100;

    public void importCsv(MultipartFile file) {
        String filename = file.getOriginalFilename();
        List<Map<String, Object>> batch = new ArrayList<>();

        String type = VehicleDataConstants.PREFIX_TO_VEHICLE_TYPE.entrySet().stream()
                .filter(entry -> filename != null && filename.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new VehicleDataException(ErrorCode.UNSUPPORTED_FILENAME.getCode(), ErrorCode.UNSUPPORTED_FILENAME.getMessage()));

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()
                             .withDelimiter(';')
                             .withIgnoreHeaderCase()
                             .withTrim())) {

            for (CSVRecord record : csvParser) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("Type", type);

                for (String header : csvParser.getHeaderNames()) {
                    dataMap.put(header, convertValue(record.get(header)));
                }

                batch.add(dataMap);

                if (batch.size() >= BATCH_SIZE) {
                    vehicleDataRepository.insertVehicles(batch, VehicleDataConstants.VEHICLE_DATA);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                vehicleDataRepository.insertVehicles(batch, VehicleDataConstants.VEHICLE_DATA);
            }

        } catch (Exception e) {
            throw new VehicleDataException(ErrorCode.CSV_IMPORT_ERROR.getCode(), ErrorCode.CSV_IMPORT_ERROR.getMessage());
        }
    }

    private Object convertValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignore) {
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignore) {
        }

        return value;
    }
}
