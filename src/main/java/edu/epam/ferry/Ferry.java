package edu.epam.ferry;

import edu.epam.ferry.entity.FerryState;
import edu.epam.ferry.entity.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ferry {

    private static final Logger logger = LogManager.getLogger(Ferry.class);

    private static Ferry instance;
    private static boolean instanceExist = false;
    private int size;
    private int liftingCapacity;
    private FerryState currentState = FerryState.EMPTY;
    private List<Vehicle> carsList = new ArrayList<>();

    private static ReentrantLock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    private Ferry() {
    }

    public static Ferry getInstance(){
        if(!instanceExist){
            lock.lock();
            try {
                if(instance == null){
                    instance = new Ferry();
                    instanceExist = true;
                }
            }
            finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setLiftingCapacity(int liftingCapacity) {
        this.liftingCapacity = liftingCapacity;
    }

    public void transfer(Vehicle vehicle) {
        try {
            addCar(vehicle);
            sailing(vehicle);
            discharging(vehicle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addCar(Vehicle vehicle) {
        try {
            lock.lock();
            while (currentState == FerryState.CHARGED) {
                logger.info("{}: all seats are occupied, waiting", Thread.currentThread().getName());
                condition.await();
            }
            if (!checkFreePlaceForVehicle(vehicle)) {
                logger.info("Ready to go!");
                currentState = FerryState.CHARGED;
                condition.signalAll();
                condition.await();
            }
            carsList.add(vehicle);
            logger.info("{}: boarded the ferry waiting for start.", Thread.currentThread().getName());
            condition.await(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void discharging(Vehicle vehicle) {
        lock.lock();
        try {
            logger.info("{} : discharge", Thread.currentThread().getName());
            carsList.remove(vehicle);
            TimeUnit.MILLISECONDS.sleep(1000);
            if (carsList.isEmpty()) {
                currentState = FerryState.EMPTY;
                condition.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void sailing(Vehicle vehicle) throws InterruptedException {
        logger.info("{} : cross the river!", Thread.currentThread().getName());
        TimeUnit.MILLISECONDS.sleep(5000);
    }

    private boolean checkFreePlaceForVehicle(Vehicle vehicle) {
        int takenSize = getTakenSize() + vehicle.getSize();
        int takenCapacity = getTakenCapacity() + vehicle.getWeight();
        boolean result = (size >= takenSize && liftingCapacity >= takenCapacity);
        logger.info("{} : ReadyToGo: {}", Thread.currentThread().getName(), result);
        return result;
    }

    private int getTakenSize() {
        int result = carsList.stream()
                .mapToInt(Vehicle::getSize)
                .sum();
        return result;
    }

    private int getTakenCapacity() {
        int result = carsList.stream()
                .mapToInt(Vehicle::getWeight)
                .sum();
        return result;
    }
}