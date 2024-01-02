package Operation;

import Entity.Client;

public class OperationTwoOpt extends Operation {
    //MODIFIER
    private final Client client1;
    private final Client client2;


    public OperationTwoOpt(Client client1, Client client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OperationTwoOpt)) {
            return false;
        }

        OperationTwoOpt other = (OperationTwoOpt) obj;

        return (this.client1.equals(other.client1) || this.client1.equals(other.client2)) && (this.client2.equals(other.client1) || this.client2.equals(other.client2));
    }

    @Override
    public String toString() {
        return "Opération : Exchange " + client1.getId() + " with " + client2.getId();
    }
}
