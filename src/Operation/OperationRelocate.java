package Operation;


import Entity.Client;
import Entity.Route;

public class OperationRelocate extends Operation {
    private final Client client;
    private final Route route;
    private final int position;


    public OperationRelocate(Client client, Route route, int position) {
        this.client = client;
        this.route = route;
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OperationRelocate)) {
            return false;
        }

        OperationRelocate other = (OperationRelocate) obj;

        return this.client.equals(other.client) && this.route.equals(other.route) && this.position == other.position;
    }

    @Override
    public String toString() {
        return "Opération : Relocate "+client.getId()+" in route "+route.getVehicle().getId()+" at position "+position;
    }
}
