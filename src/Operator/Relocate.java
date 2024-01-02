package Operator;

import Entity.*;
import Operation.Operation;
import Operation.OperationRelocate;

import java.util.ArrayList;
import java.util.HashMap;

public class Relocate extends Neighbourhood{
    private Solution solution;

    public Relocate(Solution solution){
        this.solution=solution;
    }


    @Override
    public ArrayList<Neighbor> neighborsOperator() {
        ArrayList<Neighbor> neighbors = new ArrayList<>();
        ArrayList<Route> routes = solution.getRoutes();
        //Création map client, route
        HashMap<Client, Route> allClients = new HashMap<>();
        for (Route route : routes) {
            ArrayList<Client> clients = route.getClients();
            for (Client client : clients) {
                if (client.getClass() != Depot.class) allClients.put(client, route);

            }
        }

        //On parcours tous les clients
        for (Client client : allClients.keySet()) {
            Route routeClient = allClients.get(client);
            int indexClient = routeClient.getClients().indexOf(client);

            //On supprime le client de sa route initiale
            Solution newSolution = solution.deleteClientInRoute(client, routeClient);

            //Parcours de toutes les positions
            for (Route route : newSolution.getRoutes()) {
                //On parcours la liste des clients de cette route
                for (int i = 1; i < route.getClients().size(); i++) {
                    //On ajoute le client à cette route
                    if (!(routeClient.getVehicle().getId() == route.getVehicle().getId() && indexClient == i)) {
                        Solution s = newSolution.insertClientInNewRoute(client, i, route);
                        if (s != null) {
                            Operation operation = new OperationRelocate(client, route, i);
                            neighbors.add(new Neighbor(s.getRoutes(),solution, operation));
                        }
                    }

                }
            }
        }
        return neighbors;
    }
}
