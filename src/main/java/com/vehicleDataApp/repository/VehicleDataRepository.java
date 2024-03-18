package com.vehicleDataApp.repository;

import com.vehicleDataApp.service.VehicleDataConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class VehicleDataRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public VehicleDataRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void insertVehicles(List<Map<String, Object>> vehicleData, String collectionName) {
        mongoTemplate.insert(vehicleData, VehicleDataConstants.VEHICLE_DATA);
    }
}
