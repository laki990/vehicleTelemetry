package com.vehicleDataApp.repository;

import com.vehicleDataApp.service.VehicleDataConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(VehicleDataRepository.class)
public class VehicleDataRepositoryTest extends MongoContainerInitialiserTest {

    @Autowired
    private VehicleDataRepository vehicleDataRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testInsertVehicle() {
        List<Map<String, Object>> batch = new ArrayList<>();

        Map<String, Object> vehicleData = new HashMap<>();
        vehicleData.put("Engine speed", 100);
        vehicleData.put("Type", "tractor");

        batch.add(vehicleData);
        vehicleDataRepository.insertVehicles(batch, VehicleDataConstants.VEHICLE_DATA);

        Query query = new Query();
        query.addCriteria(Criteria.where("Engine speed").is(100).and("Type").is("tractor"));
        Map<String, Object> fetchedData = mongoTemplate.findOne(query, Map.class, VehicleDataConstants.VEHICLE_DATA);

        assertThat(fetchedData).isNotNull();
        assertThat(fetchedData.get("Engine speed")).isEqualTo(100);
        assertThat(fetchedData.get("Type")).isEqualTo("tractor");
    }
}
