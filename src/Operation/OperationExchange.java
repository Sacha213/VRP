package Operation;

import Entity.Client;

public class OperationExchange extends Operation {
    private final Client client1;
    private final Client client2;


    public OperationExchange(Client client1, Client client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OperationExchange)) {
            return false;
        }

        OperationExchange other = (OperationExchange) obj;

        return (this.client1.equals(other.client1) || this.client1.equals(other.client2)) && (this.client2.equals(other.client1) || this.client2.equals(other.client2));
    }

    @Override
    public String toString() {
        return "Opération : TwoOpt " + client1.getId() + " and " + client2.getId();
    }
}
