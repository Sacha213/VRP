package Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Solution implements Comparable<Solution> {
    private final ArrayList<Route> routes;

    public Solution(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public double getTotalDistance() {
        double totalDistance = 0;
        for (Route route : routes) totalDistance += route.getDistance();
        return totalDistance;
    }


    public static Solution generateRandomSolution(ArrayList<Client> clients, Depot depot, int capacity) {
        // Créer une copie de la liste des clients pour pouvoir les supprimer une fois qu'ils sont assignés à une route
        ArrayList<Client> remainingClients = new ArrayList<>(clients);
        // Initialiser une liste vide de véhicule
        ArrayList<Route> routes = new ArrayList<>();

        // Mélanger les clients
        Collections.shuffle(remainingClients);

        Vehicle currentVehicle = new Vehicle(capacity);
        Route currentRoute = new Route(currentVehicle, depot);
        int index = 0;
        while (!remainingClients.isEmpty()) {
            Client client = remainingClients.get(index);
            if (!currentRoute.getVehicle().isFull() && currentRoute.canVisit(client)) {
                currentRoute.addClient(client);
                remainingClients.remove(index);
                if (index > remainingClients.size() - 1) {
                    index = 0;
                    currentRoute.addClient(depot);
                    routes.add(currentRoute);
                    currentVehicle = new Vehicle(capacity);
                    currentRoute = new Route(currentVehicle, depot);
                }
            } else if (index < remainingClients.size() - 1) {
                index += 1;
            } else {
                index = 0;
                currentRoute.addClient(depot);
                routes.add(currentRoute);
                currentVehicle = new Vehicle(capacity);
                currentRoute = new Route(currentVehicle, depot);
            }
        }
        return new Solution(routes);
    }

    public static Solution generateRandomSolution(ArrayList<Client> clients, Depot depot, int capacity, int nbVehicule) {
        // Créer une copie de la liste des clients pour pouvoir les supprimer une fois qu'ils sont assignés à une route
        ArrayList<Client> remainingClients = new ArrayList<>(clients);
        // Initialiser une liste vide de véhicule
        ArrayList<Route> routes = new ArrayList<>();
        Random r = new Random();

        //Création des n routes
        for(int i=0; i< nbVehicule; i++){
            Vehicle vehicle = new Vehicle(capacity);
            routes.add(new Route(vehicle, depot));
        }
        //Tant qu'il y a un client non assigné à une route
        while (!remainingClients.isEmpty()) {
            //On pioche un client aléatoirement
            int indexClient = r.nextInt(remainingClients.size());
            Client client = remainingClients.get(indexClient);

            //On parcours notre liste de route
            for(Route route : routes){
                if (!route.getVehicle().isFull() && route.canVisit(client)) {
                    route.addClient(client);
                    remainingClients.remove(indexClient);
                    break;
                }
            }

        }
        for(Route route : routes){
            route.addClient(depot);
        }

        return new Solution(routes);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    //Reprendre ICI
    public Solution insertClientInNewRoute(Client client, int indexClient, Route route) {
        //On récupère les clients de la route
        ArrayList<Client> clientsTemp = new ArrayList<>(route.getClients());
        //On ajoute le client dans cette route à l'index
        clientsTemp.add(indexClient, client);

        //On enlève l'ancienne route de la liste des routes
        ArrayList<Route> routesTemp = cloneRoutes();
        int routeIndex = routes.indexOf(route);
        routesTemp.remove(route);

        //On crée notre nouvelle route
        Route routeTemp = route.copy(clientsTemp);
        //Si celle-ci est possible alors on l'ajoute dans notre solution
        if (routeTemp != null) {
            routesTemp.add(routeIndex, routeTemp);
            return new Solution(routesTemp);
        } else {
            return null;
        }
    }

    public Solution deleteClientInRoute(Client client, Route route) {
        //On récupère les clients de la route
        ArrayList<Client> clients = new ArrayList<>(route.getClients());
        //On supprime le client dans cette route
        clients.remove(client);

        //On copy notre liste de route
        ArrayList<Route> routesTemp = cloneRoutes();
        int routeIndex = routes.indexOf(route);
        routesTemp.remove(route);


        // On créer notre nouvelle route
        Route newRoute = route.copy(clients);
        routesTemp.add(routeIndex, newRoute);

        return new Solution(routesTemp);
    }

    @Override
    public int compareTo(Solution other) {
        System.out.println("A supprimer");
        for (int i = 0; i < this.routes.size(); i++) {
            if (!other.routes.contains(this.routes.get(i))) return -1;
        }
        return 0;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Solution)) {
            return false;
        }

        Solution other = (Solution) obj;
        for (int i = 0; i < this.routes.size(); i++) {
            if (!other.routes.contains(this.routes.get(i))) return false;
        }
        return true;
    }

    public ArrayList<Route> cloneRoutes(){
        ArrayList<Route> cloneRoute = new ArrayList<>();
        for (Route r : routes) {
            try {
                cloneRoute.add((Route) r.clone());
            } catch (CloneNotSupportedException e) {
                System.out.println(e);
            }
        }
        return cloneRoute;
    }
}