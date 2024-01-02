package Entity;

public class Client implements Comparable<Client> {
    private final String id;
    private final double x;
    private final double y;
    private final int readyTime;
    private final int dueTime;
    private final int demand;
    private final int serviceTime;
    private final double distanceFromDepot;

    public Client(String id, int x, int y, int readyTime, int dueTime, int demand, int serviceTime, Depot depot) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.demand = demand;
        this.serviceTime = serviceTime;
        this.distanceFromDepot = this.distanceTo(depot);
    }

    public Client(String id, int x, int y, int readyTime, int dueTime, int demand, int serviceTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.demand = demand;
        this.serviceTime = serviceTime;
        this.distanceFromDepot = 0;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public int getDueTime() {
        return dueTime;
    }

    public int getDemand() {
        return demand;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public double getDistanceFromDepot() {
        return distanceFromDepot;
    }

    // Méthode pour calculer la distance entre le client et un autre client
    public double distanceTo(Client other) {
        double dx = getX() - other.getX();
        double dy = getY() - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    @Override
    public int compareTo(Client other) {
        System.out.println("a delete");
        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Client)) {
            return false;
        }
        Client other = (Client) obj;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Entity.Client{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", readyTime=" + readyTime +
                ", dueTime=" + dueTime +
                ", demand=" + demand +
                ", serviceTime=" + serviceTime +
                ", distanceFromDepot=" + distanceFromDepot +
                '}';
    }
}
