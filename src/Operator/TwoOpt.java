package Operator;

import Entity.Client;
import Entity.Neighbor;
import Entity.Route;
import Entity.Solution;
import Operation.Operation;
import Operation.OperationTwoOpt;

import java.util.ArrayList;

public class TwoOpt extends Neighbourhood{
    private Solution solution;

    public TwoOpt(Solution solution){
        this.solution=solution;
    }

    @Override
    public ArrayList<Neighbor> neighborsOperator() {
        ArrayList<Neighbor> neighbors = new ArrayList<>();

        //On parcours les routes
        ArrayList<Route> routes = solution.getRoutes();
        for (Route route : routes) {
            //On parcours toutes les possibilité
            ArrayList<Client> clients = route.getClients();
            for(int i=1;  i< clients.size() - 1; i++){
                //i+3 min pour avoir deux arrete avec des sommets différents
                for(int j=i+3;j < clients.size() - 1; j++){
                    //On crée un nouveau voisin avec deux arrêtes échangées
                    ArrayList<Client> newCLients = new ArrayList<>();
                    for(int k=0; k<=i;k++){
                        newCLients.add(clients.get(k));
                    }
                    for(int k=j-1; k>i;k--){
                        newCLients.add(clients.get(k));
                    }
                    for(int k=j; k<clients.size();k++){
                        newCLients.add(clients.get(k));
                    }
                    Route newRoute = route.copy(newCLients);
                    if(newRoute!=null){
                        ArrayList<Route> routesTemp = solution.cloneRoutes();
                        int routeIndex = routes.indexOf(route);
                        routesTemp.remove(route);
                        routesTemp.add(routeIndex, newRoute);
                        Solution newSolution = new Solution(routesTemp);
                        Operation operation = new OperationTwoOpt(clients.get(i),clients.get(j));
                        neighbors.add(new Neighbor(newSolution.getRoutes(),solution, operation));
                    }
                    else {
                        //System.out.println("False : two-opt " + clients.get(i).getId() + " and " + clients.get(j).getId());
                    }
                }
            }
        }

        return neighbors;
    }
}
