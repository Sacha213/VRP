package Util;

import Entity.Client;
import Entity.Depot;
import Entity.Route;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;

public class Graph {
    private org.graphstream.graph.Graph graph;

    public Graph(ArrayList<Client> clients, Depot depot) {
        // Créer un nouveau graphique
        graph = new SingleGraph("VRPTW");
        graph.setAttribute("ui.stylesheet", "node { text-size: 20px; } edge { text-size: 20px; }");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setStrict(false);

        // Ajouter le noeud du dépôt
        Node depotNode = graph.addNode(depot.getId());
        depotNode.setAttribute("ui.label", "Entity.Depot");
        depotNode.setAttribute("xy", depot.getX(), depot.getY());
        depotNode.setAttribute("ui.style", "fill-color: rgb(255,0,0); size: 1px;");

        // Ajouter les noeuds des clients
        for (Client client : clients) {
            Node clientNode = graph.addNode(client.getId());
            clientNode.setAttribute("ui.label", "    " + client.getId());
            clientNode.setAttribute("xy", client.getX(), client.getY());
            depotNode.setAttribute("ui.style", "fill-color: rgb(255,0,0); size: 1px;");

        }

        // Afficher le graphique
        Viewer viewer = graph.display();
        viewer.disableAutoLayout();
    }

    public void updateGraph(ArrayList<Route> routes) {

        for (int i = graph.getEdgeCount()-1; i >= 0; i--) {
            graph.removeEdge(i);
        }


        // Ajouter les arêtes représentant les chemins des véhicules
        int colorIndex = 0;
        for (Route route : routes) {
            ArrayList<Client> vehicleClients = route.getClients();
            String color = "rgb(" + (colorIndex % 256) + ", " + (255 - colorIndex % 256) + ", " + (colorIndex % 128) + ")";
            for (int i = 0; i < vehicleClients.size() - 1; i++) {
                Node node1 = graph.getNode(vehicleClients.get(i).getId());
                Node node2 = graph.getNode(vehicleClients.get(i + 1).getId());
                Edge edge = graph.addEdge(node1.getId() + "-" + node2.getId(), node1, node2);
                if (edge != null) {
                    edge.setAttribute("vehicle", route.getVehicle().getId());
                    edge.setAttribute("ui.style", "fill-color: " + color + "; size: 1px;");
                }
            }
            colorIndex += 100;
        }




    }
}
