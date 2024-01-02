package Operator;

import Entity.Neighbor;
import Entity.Solution;

import java.util.ArrayList;

public abstract class Neighbourhood {

    public static ArrayList<Neighbor> getVoisins(Solution solution) {
        ArrayList<Neighbor> neighbors = new ArrayList<>();
        neighbors.addAll(new Relocate(solution).neighborsOperator());
        neighbors.addAll(new Reverse(solution).neighborsOperator());
        neighbors.addAll(new Exchange(solution).neighborsOperator());
        neighbors.addAll(new TwoOpt(solution).neighborsOperator());
        return neighbors;
    }

    public abstract ArrayList<Neighbor> neighborsOperator();

}
