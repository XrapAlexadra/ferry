package edu.epam.ferry.factory;

import edu.epam.ferry.entity.Vehicle;
import edu.epam.ferry.entity.VehicleType;
import edu.epam.ferry.exception.VehicleException;
import edu.epam.ferry.validator.VehicleValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class VehicleFactory {

    private static final Logger logger = LogManager.getLogger(VehicleFactory.class);

    private static VehicleFactory instance = new VehicleFactory();


    private VehicleFactory() {
    }

    public static VehicleFactory getInstance() {
        return instance;
    }

    public Vehicle createVehicle(List<Integer> data) throws VehicleException {
        if (!VehicleValidator.isValidData(data)) {
            logger.error("Invalid data: {}", data);
            throw new VehicleException("Invalid data: " + data);
        }
        Vehicle vehicle;
        int size = data.get(0);
        int weight = data.get(1);
        VehicleType state = (size > 4 || weight > 4) ? VehicleType.TRUCK : VehicleType.CAR;
        vehicle = new Vehicle(size, weight, state);
        logger.info("Create new Vehicle: {}", vehicle);
        return vehicle;
    }
}
