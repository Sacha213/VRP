package Metaheuristique;

import Entity.Neighbor;
import Entity.Solution;
import Operation.Operation;
import Util.Graph;

import java.util.ArrayList;
import java.util.Comparator;

import static Operator.Neighbourhood.getVoisins;

public class Tabou implements Metaheuristique{
    private Solution solutionInitial;
    private int tailleListTabouc;
    private Graph graph;
    private int iterations;

    public Tabou(Solution solutionInitial, int tailleListTabouc, int iterations, Graph graph){
        this.solutionInitial = solutionInitial;
        this.tailleListTabouc = tailleListTabouc;
        this.graph = graph;
        this.iterations = iterations;
    }

    @Override
    public Solution search() {
        int nBSolutionsGenerer = 0;
        Solution solutionOptimal = solutionInitial;

        //Initialisation de la liste taboue
        ArrayList<Operation> listTabou = new ArrayList<>();

        Solution solutionActuelle = solutionInitial;

        for (int i = 0; i < iterations; i++) {
            ArrayList<Neighbor> voisins = getVoisins(solutionActuelle);
            nBSolutionsGenerer+=voisins.size();

            //On supprime les solutions taboues
            for (Operation operationTabou : listTabou) {
                if (voisins.removeIf(voisin -> voisin.getOperation().equals(operationTabou)));
            }

            //On récupère la meilleure des solutions trouvées
            voisins.sort(Comparator.comparingDouble(Solution::getTotalDistance));
            Neighbor solutionNouvelle = voisins.get(0);


            //On supprime les solutions égales à la précédente
            Solution finalMeilleurSolution = solutionNouvelle;
            if (voisins.removeIf(voisin -> voisin.equals(finalMeilleurSolution)));


            //Mise à jour de la liste tabou
            if (solutionNouvelle.getTotalDistance() >= solutionActuelle.getTotalDistance()) {
                listTabou.add(solutionNouvelle.getOperation());
                if (listTabou.size() > tailleListTabouc) listTabou.remove(0);
            }

            //Sauvegarde de la meilleure solution
            if (solutionNouvelle.getTotalDistance() < solutionOptimal.getTotalDistance()) {
                solutionOptimal = solutionNouvelle;
            }
            //On actualise la solution actuelle
            solutionActuelle = solutionNouvelle;
            graph.updateGraph(solutionActuelle.getRoutes());
        }

        System.out.println("Nombre de solutions généré "+nBSolutionsGenerer);
        return solutionOptimal;
    }


}
