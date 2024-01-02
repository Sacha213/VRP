package Operation;

import Entity.Route;

public class OperationReverse extends Operation {
    private final Route route;


    public OperationReverse(Route route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OperationReverse)) {
            return false;
        }

        OperationReverse other = (OperationReverse) obj;

        return this.route.equals(other.route);
    }

    @Override
    public String toString() {
        return "Opération : Reverse "+route.getVehicle().getId();
    }
}
