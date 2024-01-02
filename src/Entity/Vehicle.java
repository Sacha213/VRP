package Entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Vehicle {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private int currentLoad;
    private final int capacity;

    public Vehicle(int capacity) {
        id = count.incrementAndGet();
        this.currentLoad = capacity;
        this.capacity = capacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void decreaseCurrentLoad(int load) {
        this.currentLoad -= load;
    }

    public int getId() {
        return id;
    }

    public boolean isFull() {
        return this.currentLoad == 0;
    }

    public Vehicle copy() {
        Vehicle copyVehicle = new Vehicle(this.capacity);
        copyVehicle.id = this.id;
        return copyVehicle;
    }



}
