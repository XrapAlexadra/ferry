package edu.epam.ferry.exception;

public class VehicleException extends Exception {

    public VehicleException() {
        super();
    }

    public VehicleException(String s) {
        super(s);
    }

    public VehicleException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public VehicleException(Throwable throwable) {
        super(throwable);
    }
}
