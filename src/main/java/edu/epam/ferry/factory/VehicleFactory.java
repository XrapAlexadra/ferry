package edu.epam.ferry.factory;

import edu.epam.ferry.entity.Car;
import edu.epam.ferry.entity.Truck;
import edu.epam.ferry.entity.Vehicle;
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
        if(!VehicleValidator.isValidData(data)){
            logger.error("Invalid data: {}", data);
            throw new VehicleException("Invalid data: " + data);
        }
        Vehicle vehicle;
        int size = data.get(0);
        int weight = data.get(1);
        if(size > 4 || weight > 4){
            vehicle = new Truck(size, weight);
            logger.info("Create new Car: {}", vehicle);
        }
        else {
            vehicle = new Car(size, weight);
            logger.info("Create new Truck: {}", vehicle);
        }
        return vehicle;
    }
}
