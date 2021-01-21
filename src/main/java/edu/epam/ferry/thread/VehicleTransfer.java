package edu.epam.ferry.thread;

import edu.epam.ferry.entity.Ferry;
import edu.epam.ferry.entity.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class VehicleTransfer implements Callable<Vehicle> {

    private static final Logger logger = LogManager.getLogger(VehicleTransfer.class);

    private Vehicle vehicle;

    public VehicleTransfer(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public Vehicle call(){
        logger.info("{}: moving to the ferry ({})", Thread.currentThread().getName(), vehicle);
        Ferry.getInstance().transfer(vehicle);
        return vehicle;
    }

}
