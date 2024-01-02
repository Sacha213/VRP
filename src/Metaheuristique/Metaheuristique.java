package Metaheuristique;

import Entity.*;
import Operation.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static Operator.Neighbourhood.getVoisins;

public interface Metaheuristique {

    public Solution search();


    public static ArrayList<Solution> relocateInterOld(Solution solution) {
        ArrayList<Solution> voisins = new ArrayList<>();

        ArrayList<Route> routes = solution.getRoutes();
        //Création map client, route
        HashMap<Client, Route> clientsMap = new HashMap<>();
        for (Route route : routes) {
            ArrayList<Client> clients = route.getClients();
            for (Client client : clients) {
                if (client.getClass() != Depot.class) clientsMap.put(client, route);

            }
        }


        //On parcours tous les clients
        for (Client client : clientsMap.keySet()) {
            //Parcours de toutes les positions
            for (Route route : routes) {
                if (route != clientsMap.get(client)) {
                    //On parcours la liste des clients de cette route
                    int size = route.getClients().size();
                    for (int i = 1; i < size; i++) {
                        ArrayList<Client> clientsTemp = new ArrayList<>(route.getClients());
                        //On ajoute le client
                        clientsTemp.add(i, client);
                        ArrayList<Route> routesTemp = new ArrayList<>(routes);
                        int routeIndex = routesTemp.indexOf(route);
                        routesTemp.remove(route);
                        Route routeTemp = route.copy(clientsTemp);

                        if (routeTemp != null) {
                            int routeIndex2 = routesTemp.indexOf(clientsMap.get(client));
                            routesTemp.remove(clientsMap.get(client));
                            //On supprime le client de sa route initial
                            ArrayList<Client> routeClient = new ArrayList<>(clientsMap.get(client).getClients());
                            if (routeClient.size() <= 2) {
                                routeClient.remove(client);
                                Route routeTemp2 = route.copy(routeClient);
                                routesTemp.add(routeIndex2, routeTemp2);

                                routeIndex -= 1;

                            }
                            routesTemp.add(routeIndex, routeTemp);
                            voisins.add(new Solution(routesTemp));
                        }


                    }
                }

            }

        }


        return voisins;
    }

    public static ArrayList<Solution> relocateIntra(Solution solution) {
        ArrayList<Solution> voisins = new ArrayList<>();

        //Parcourir les routes
        ArrayList<Route> routes = solution.getRoutes();
        for (Route route : routes) {
            ArrayList<Client> clients = route.getClients();
            //Parcours de tous les clients

            for (int i = 1; i < clients.size() - 1; i++) {
                //Parcours de toutes les positions
                Client client = clients.get(i);
                ArrayList<Client> clientsTemp = new ArrayList<>(clients);
                clientsTemp.remove(i);
                for (int j = 1; j < clients.size() - 1; j++) {
                    if (j != i) {
                        clientsTemp.add(j, client);
                        ArrayList<Route> routesTemp = new ArrayList<>(routes);
                        int routeIndex = routesTemp.indexOf(route);
                        routesTemp.remove(route);
                        Route routeTemp = route.copy(clientsTemp);

                        if (routeTemp != null) {
                            routesTemp.add(routeIndex, routeTemp);
                            voisins.add(new Solution(routesTemp));
                        }

                    }
                }

            }
        }

        return voisins;
    }


    public static ArrayList<Solution> exchangeIntra(Solution solution) {
        ArrayList<Solution> voisins = new ArrayList<>();

        //Parcourir les routes
        ArrayList<Route> routes = solution.getRoutes();
        for (Route route : routes) {
            ArrayList<Client> clients = route.getClients();
            //Parcours de tous les clients sauf les dépôts
            for (int i = 1; i < clients.size() - 1; i++) {
                //Parcours de tous les échanges
                ArrayList<Client> clientsTemp = new ArrayList<>(clients);
                for (int j = i; j < clients.size() - 1; j++) {
                    Collections.swap(clientsTemp, i, j);
                    ArrayList<Route> routesTemp = new ArrayList<>(routes);
                    int routeIndex = routesTemp.indexOf(route);
                    routesTemp.remove(route);
                    Route routeTemp = route.copy(clientsTemp);

                    if (routeTemp != null) {
                        routesTemp.add(routeIndex, routeTemp);
                        voisins.add(new Solution(routesTemp));


                    }
                }

            }
        }

        return voisins;
    }


}
