package Metaheuristique;

import Entity.Neighbor;
import Entity.Solution;
import Util.Graph;

import java.util.ArrayList;

import static Operator.Neighbourhood.getVoisins;

public class SimulatedAnnealing implements Metaheuristique{
    private Solution solutionInitial;
    private Graph graph;
    private double finalTemperature;
    private int iterations;
    private double coolingRate;

    public SimulatedAnnealing(Solution solutionInitial, Graph graph, double finalTemperature, int iterations, double coolingRate){
        this.solutionInitial = solutionInitial;
        this.graph = graph;
        this.finalTemperature = finalTemperature;
        this.iterations = iterations;
        this.coolingRate = coolingRate;
    }

    @Override
    public Solution search() {
        int nBSolutionsGenerer = 0;

        Solution xMin = solutionInitial;
        double fitnessMin = xMin.getTotalDistance();
        Solution solutionActuelle = solutionInitial;
        double temperature = getInitialTemperature(solutionInitial);
        while (temperature>finalTemperature){
            for(int l=1; l<=iterations;l++){
                ArrayList<Neighbor> neighbors = getVoisins(solutionActuelle);
                nBSolutionsGenerer+=neighbors.size();
                int random = (int)(Math.random() * (neighbors.size()));
                Solution y = neighbors.get(random);
                if(y.getTotalDistance() - solutionActuelle.getTotalDistance()<=0){
                    solutionActuelle = y;
                    if(solutionActuelle.getTotalDistance()<fitnessMin){
                        xMin = y;
                        fitnessMin = solutionActuelle.getTotalDistance();
                    }
                }
                else{
                    double p = Math.random();
                    if(p<=Math.exp(-(y.getTotalDistance() - solutionActuelle.getTotalDistance())/temperature))solutionActuelle = y;
                }
                graph.updateGraph(solutionActuelle.getRoutes());
            }
            temperature*=coolingRate;
        }
        System.out.println("Nombre de solutions généré "+nBSolutionsGenerer);
        return xMin;
    }

    public static double getInitialTemperature(Solution solution){
        double maxDelta = 0;
        ArrayList<Neighbor> neighbors = getVoisins(solution);
        for(Neighbor neighbor : neighbors){
            double delta = neighbor.getTotalDistance() - solution.getTotalDistance();
            if(delta>maxDelta)maxDelta=delta;
        }

        return -maxDelta/Math.log(0.8);
    }
}
