package Entity;
import Operation.Operation;

import java.util.ArrayList;

public class Neighbor extends Solution{
    private final Solution previousSolution;
    private final Operation operation;

    public Neighbor(ArrayList<Route> routes, Solution previousSolution, Operation operation) {
        super(routes);
        this.previousSolution = previousSolution;
        this.operation = operation;
    }

    public Solution getPreviousSolution() {
        return previousSolution;
    }

    public Operation getOperation() {
        return operation;
    }


}
