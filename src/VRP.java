import Entity.Client;
import Entity.Route;
import Entity.Solution;
import Metaheuristique.*;
import Util.Graph;
import Util.InputDataReader;
import Util.VRPInstance;

import java.util.ArrayList;

public class VRP {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        long start;
        Solution bestSolution;
        int iterations;

        /** Importation du jeu de données **/
        //Vous pouvez modifier le jeu de données ici
        InputDataReader inputDataReader = new InputDataReader("Data/data1201.vrp");
        VRPInstance vrpInstance = inputDataReader.readData();

        /**Calcul du nombre minimal de véhicules */
        int somDemand = 0;
        for (Entity.Client c : vrpInstance.getClients()) {
            somDemand += c.getDemand();
        }
        System.out.println("Le nombre minimal de véhicules pour ce jeu de données est " + somDemand / vrpInstance.getCapacity());

        //Génération d'une solution aléatoire
        //Choisissez ici parmi les deux fonctions de générations de solutions initiales
        Solution randomolution = Solution.generateRandomSolution(vrpInstance.getClients(), vrpInstance.getDepot(), vrpInstance.getCapacity());
        //Solution randomolution = Solution.generateRandomSolution(vrpInstance.getClients(),vrpInstance.getDepot(),vrpInstance.getCapacity(),60);

        //Instanciation du graph
        Graph graph = new Graph(vrpInstance.getClients(), vrpInstance.getDepot());
        Graph graphSolutionRandom = new Graph(vrpInstance.getClients(), vrpInstance.getDepot());
        graphSolutionRandom.updateGraph(randomolution.getRoutes());


        /** Tabou **/

        int tailleListeTabou = 75;
        iterations = 750;

        start = System.currentTimeMillis();
        System.out.println("Tabou");
        Metaheuristique tabou = new Tabou(randomolution,tailleListeTabou,iterations,graph);
        bestSolution = tabou.search();

        System.out.println("Départ : "+randomolution.getTotalDistance());
        System.out.println("Fini : "+bestSolution.getTotalDistance());
        System.out.println("Run time : " + (System.currentTimeMillis() - start));
        System.out.println("Nombre de véhicule :"+ getNombreVehicule(bestSolution.getRoutes()));


        //Affichage de la meilleure solution
        graph.updateGraph(bestSolution.getRoutes());


        /** Recuit simulé **/

        /*
        double finalTemperature = 0.1;
        iterations = 1000;
        double coolingRate = 0.8;

        start = System.currentTimeMillis();
        System.out.println("Recuit simuilé");
        Metaheuristique simulatedAnnealing = new SimulatedAnnealing(randomolution, graph,finalTemperature,iterations,coolingRate);
        bestSolution = simulatedAnnealing.search();

        System.out.println("Départ : "+randomolution.getTotalDistance());
        System.out.println("Fini : "+bestSolution.getTotalDistance());
        System.out.println("Run time : " + (System.currentTimeMillis() - start));
        System.out.println("Nombre de véhicule :"+ getNombreVehicule(bestSolution.getRoutes()));


        //Affichage de la meilleure solution
        graph.updateGraph(bestSolution.getRoutes());
        */

    }


    public static double calculerDistanceTotale(ArrayList<Route> listeRoutes) {
        double distanceTotale = 0;

        for (Route route : listeRoutes) {
            double distanceRoute = 0;

            for (int i = 1; i < route.getClients().size(); i++) {
                Client clientCourant = route.getClients().get(i);
                Client clientPrecedent = route.getClients().get(i - 1);
                double distanceEntreClients = Math.sqrt(Math.pow(clientCourant.getX() - clientPrecedent.getX(), 2) + Math.pow(clientCourant.getY() - clientPrecedent.getY(), 2));
                distanceRoute += distanceEntreClients;
            }

            distanceTotale += distanceRoute;
        }

        return distanceTotale;
    }

    public static int getNombreVehicule(ArrayList<Route> listeRoutes){
        int nbVehicule = 0;
        for(Route route : listeRoutes){
            //Il y a 2 clients de base --> Dépôt et Dépôt
            if(route.getClients().size()>2)nbVehicule+=1;
        }
        return nbVehicule;
    }


}
