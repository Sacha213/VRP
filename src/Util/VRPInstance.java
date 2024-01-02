package Util;

import Entity.Client;
import Entity.Depot;

import java.util.ArrayList;

public class VRPInstance {
    private Depot depot;
    private ArrayList<Client> clients;
    private int capacity;

    public VRPInstance(Depot depot, ArrayList<Client> clients, int capacity) {
        this.depot = depot;
        this.clients = clients;
        this.capacity = capacity;
    }

    public Depot getDepot() {
        return depot;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public int getCapacity() {
        return capacity;
    }
}
