package Entity;

import java.util.ArrayList;

public class Route implements Comparable<Route>, Cloneable {
    private final Vehicle vehicle;
    private final ArrayList<Client> clients;
    private double distance;
    private double time;
    private final Depot depot;

    public Route(Vehicle vehicle, Depot depot) {
        this.vehicle = vehicle;
        this.clients = new ArrayList<>();
        this.clients.add(depot);
        this.distance = 0;
        this.time = 0;
        this.depot = depot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public double getDistance() {
        return distance;
    }

    public boolean addClient(Client client) {
        if (canVisit(client)) {
            clients.add(client);
            vehicle.decreaseCurrentLoad(client.getDemand());
            updateDistance();
            return true;
        } else {
            return false;
        }
    }

    public void updateDistance() {
        Client oldClient = clients.get(clients.size() - 2);
        Client newClient = clients.get(clients.size() - 1);
        distance += newClient.distanceTo(oldClient);
        time+=newClient.distanceTo(oldClient);
        //On simule l'attente
        if (time < newClient.getReadyTime()) {
            time = newClient.getReadyTime();
        }
        time += newClient.getServiceTime();
    }


    public boolean canVisit(Client c) {
        double distanceFromClient = clients.get(clients.size() - 1).distanceTo(c);
        double distanceFromDepot = c.getDistanceFromDepot();

        // Vérifier si le client est disponible pendant la fenêtre de temps
        if (time + distanceFromClient > c.getDueTime()) {
            return false;
        }

        // Vérifier si la capacité du véhicule est suffisante pour livrer le client
        if (vehicle.getCurrentLoad() < c.getDemand()) {
            return false;
        }

        // Vérifier si le véhicule peut rentrer au dépôt avant l'heure limite
        if(time + distanceFromClient >= c.getReadyTime()){
            if (time + distanceFromClient + c.getServiceTime() + distanceFromDepot > depot.getDueTime()) {
                return false;
            }
        }
        else{
            if (c.getReadyTime() + c.getServiceTime() + distanceFromDepot > depot.getDueTime()) {
                return false;
            }
        }


        return true;
    }

    public Route copy(ArrayList<Client> clients) {
        Route copy = new Route(vehicle.copy(), depot);
        for (int i = 1; i < clients.size(); i++) {
            if (!copy.addClient(clients.get(i))) return null;
        }

        return copy;
    }

    @Override
    public int compareTo(Route other) {
        System.out.println("a delete");
        for (int i = 0; i < this.clients.size(); i++) {
            int cmp = this.clients.get(i).compareTo(other.clients.get(i));
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }

        Route other = (Route) obj;
        for (int i = 0; i < this.clients.size(); i++) {
            if (!this.clients.get(i).equals(other.clients.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        String string = "{";

        for (Client client : clients) {
            string += " " + client.getId() + " ";
        }
        string += "}";

        return string;
    }

}
