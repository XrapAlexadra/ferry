package edu.epam.ferry.runner;

import edu.epam.ferry.entity.Ferry;
import edu.epam.ferry.entity.Vehicle;
import edu.epam.ferry.exception.VehicleException;
import edu.epam.ferry.factory.VehicleFactory;
import edu.epam.ferry.parser.VehicleParser;
import edu.epam.ferry.reader.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class);

    public static void main( String[] args ) {
        List<Vehicle> vehicleList = createVehicleListFromFile();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Ferry ferry = Ferry.getInstance();
        ferry.setSize(15);
        ferry.setLiftingCapacity(15);
        for (Vehicle vehicle : vehicleList) {
            executorService.submit(vehicle);
        }

    }

    private static List<Vehicle> createVehicleListFromFile(){
        DataReader dataReader = DataReader.getInstance();
        List<String> data = dataReader.readLines();
        VehicleParser parser = VehicleParser.getInstance();
        VehicleFactory factory = VehicleFactory.getInstance();
        List<Vehicle> vehicleList = new ArrayList<>();
        for(String line: data){
            List<Integer> parseLine = parser.parse(line);
            try {
                Vehicle vehicle = factory.createVehicle(parseLine);
                vehicleList.add(vehicle);
            } catch (VehicleException e) {
                logger.info("Impossible to create vehicle. Data is invalid: {}", line);
            }
        }
        return vehicleList;
    }
}
