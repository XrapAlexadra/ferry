package edu.epam.ferry.entity;

public abstract class Vehicle{

    private final int size;
    private final int weight;

    public Vehicle(int size, int weight) {
        this.size = size;
        this.weight = weight;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "size=" + size +
                ", weight=" + weight +
                '}';
    }
}
