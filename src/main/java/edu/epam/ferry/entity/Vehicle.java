package edu.epam.ferry.entity;

public class Vehicle implements Runnable {

    private int size;
    private int weight;
    private VehicleType type;
    private Ferry ferry = Ferry.getInstance();

    public Vehicle(int size, int weight, VehicleType type) {
        this.size = size;
        this.weight = weight;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public void run() {
        ferry.transfer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return size == vehicle.size &&
                weight == vehicle.weight &&
                type == vehicle.type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = size;
        result = prime * result + weight;
        result = prime * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "size=" + size +
                ", weight=" + weight +
                ", type=" + type +
                '}';
    }
}
