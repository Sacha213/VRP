package Operator;

import Entity.*;
import Operation.Operation;
import Operation.OperationExchange;

import java.util.ArrayList;
import java.util.HashMap;

public class Exchange extends Neighbourhood {
    private Solution solution;

    public Exchange(Solution solution){
        this.solution=solution;
    }

    @Override
    public ArrayList<Neighbor> neighborsOperator() {
        ArrayList<Neighbor> neighbors = new ArrayList<>();

        ArrayList<Route> routes = solution.getRoutes();
        //Création map client, route
        HashMap<Client, Route> clientsMap = new HashMap<>();
        for (Route route : routes) {
            ArrayList<Client> clients = route.getClients();
            for (Client client : clients) {
                if (client.getClass() != Depot.class) clientsMap.put(client, route);

            }
        }

        for (int i = 0; i < clientsMap.size(); i++) {
            for (int j = i + 1; j < clientsMap.size(); j++) {
                Client client1 = (Client) clientsMap.keySet().toArray()[i];
                Client client2 = (Client) clientsMap.keySet().toArray()[j];
                if(!clientsMap.get(client1).equals(clientsMap.get(client2))){
                    //On swap
                    int indexClient1 = clientsMap.get(client1).getClients().indexOf(client1);
                    int indexClient2 = clientsMap.get(client2).getClients().indexOf(client2);
                    int indexRouteClient1 = routes.indexOf(clientsMap.get(client1));
                    int indexRouteClient2 = routes.indexOf(clientsMap.get(client2));

                    //On supprime le client1 et le client 2 de leurs routes initiales
                    Solution newSolution = solution.deleteClientInRoute(client1, solution.getRoutes().get(indexRouteClient1));
                    newSolution = newSolution.deleteClientInRoute(client2, newSolution.getRoutes().get(indexRouteClient2));

                    newSolution = newSolution.insertClientInNewRoute(client1, indexClient2, newSolution.getRoutes().get(indexRouteClient2));
                    if (newSolution != null) {
                        newSolution = newSolution.insertClientInNewRoute(client2, indexClient1, newSolution.getRoutes().get(indexRouteClient1));
                        if (newSolution != null) {
                            Operation operation = new OperationExchange(client1,client2);
                            neighbors.add(new Neighbor(newSolution.getRoutes(),solution, operation));
                        }

                    }
                }



            }
        }


        return neighbors;
    }
}
