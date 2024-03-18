package com.vehicleDataApp.service;

import com.vehicleDataApp.dto.VehicleFilter;
import com.vehicleDataApp.exceptions.ErrorCode;
import com.vehicleDataApp.exceptions.VehicleDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class VehicleDataService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Map> findVehicleDataByCriteria(List<VehicleFilter> criteriaList) {
        Query query = new Query();
        for (VehicleFilter criterion : criteriaList) {
            String operation = criterion.getOperation() != null ? criterion.getOperation() : "Equals";
            Criteria criteria;

            switch (operation) {
                case "GreaterThan":
                case "LessThan":
                    if (!(criterion.getValue() instanceof Number)) {
                        throw new VehicleDataException(ErrorCode.UNSUPPORTED_OPERATION.getCode(), ErrorCode.UNSUPPORTED_OPERATION.getMessage());
                    }
                    criteria = applyNumericOperation(criterion.getField(), (Number) criterion.getValue(), operation);
                    break;
                case "Contains":
                    if (!(criterion.getValue() instanceof String)) {
                        throw new VehicleDataException(ErrorCode.UNSUPPORTED_OPERATION.getCode(), ErrorCode.UNSUPPORTED_OPERATION.getMessage());
                    }
                    criteria = Criteria.where(criterion.getField()).regex(Pattern.quote(criterion.getValue().toString()), "i");
                    break;
                case "Equals":
                default:
                    criteria = Criteria.where(criterion.getField()).is(criterion.getValue());
                    break;
            }

            query.addCriteria(criteria);
        }

        return mongoTemplate.find(query, Map.class, VehicleDataConstants.VEHICLE_DATA);
    }

    private Criteria applyNumericOperation(String field, Number value, String operation) {
        switch (operation) {
            case "GreaterThan":
                return Criteria.where(field).gt(value);
            case "LessThan":
                return Criteria.where(field).lt(value);
            default:
                return null;
        }
    }
}
