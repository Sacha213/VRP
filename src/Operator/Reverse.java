package Operator;

import Entity.Client;
import Entity.Neighbor;
import Entity.Route;
import Entity.Solution;
import Operation.Operation;
import Operation.OperationReverse;

import java.util.ArrayList;
import java.util.Collections;

public class Reverse extends Neighbourhood{
    private Solution solution;

    public Reverse(Solution solution){
        this.solution=solution;
    }

    @Override
    public ArrayList<Neighbor> neighborsOperator() {
        ArrayList<Neighbor> neighbors = new ArrayList<>();

        //Parcourir les routes
        ArrayList<Route> routes = solution.getRoutes();
        for (Route route : routes) {
            //Si plus que 1 client et deux dépôts
            if (route.getClients().size() > 3) {
                //Parcours de tous les clients sauf le dépot
                ArrayList<Client> listClients = new ArrayList<>(route.getClients());
                Collections.reverse(listClients);
                ArrayList<Route> listRoutes = solution.cloneRoutes();
                int routeIndex = listRoutes.indexOf(route);
                listRoutes.remove(route);
                Route newRoute = route.copy(listClients);
                if (newRoute != null) {
                    listRoutes.add(routeIndex, newRoute);
                    Solution s = new Solution(listRoutes);
                    Operation operation = new OperationReverse(route);
                    neighbors.add(new Neighbor(s.getRoutes(),solution, operation));
                }
            }
        }


        return neighbors;
    }
}
