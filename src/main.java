import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {

    public static Agent[] shuffleAgents(Agent[] agents){
        List<Agent> agentList = Arrays.asList(agents);
        Collections.shuffle(agentList);
        return agentList.toArray(agents);
    }

    public static int partie1(boolean verbose){
        Agent a = new Agent("A", null, null);
        Agent b = new Agent("B", a, null);
        Agent c = new Agent("C", b, null);
        Agent d = new Agent("D", c, null);
        Agent[] agents = shuffleAgents(new Agent[]{a, b, c, d});
        Environnement environnement = new Environnement(3, agents, 1000, verbose);
        for(Agent agent : agents){
            agent.setEnvironnement(environnement);
        }
        return environnement.start();
    }

    public static int partie2(boolean verbose){
        Agent a = new AgentCoordonne("A", null, null);
        Agent b = new AgentCoordonne("B", a, null);
        Agent c = new AgentCoordonne("C", b, null);
        Agent d = new AgentCoordonne("D", c, null);
        Agent[] agents = shuffleAgents(new Agent[]{a, b, c, d});
        Environnement environnement = new Environnement(3, agents, 1000, verbose);
        for(Agent agent : agents){
            agent.setEnvironnement(environnement);
        }
        return environnement.start();
    }


    public static void main(String[] args){
        System.out.println("Simulation de la partie 1 :");
        partie1(true);
        System.out.println(" \n \nSimulation de la partie 2 :");
        partie2(true);
        int nbParties = 100;
        System.out.println(" \n \nSimulation de "+ nbParties +" parties 1 :");
        int totalMovesP1 = 0;
        for(int i =0; i<nbParties ; i++){
            totalMovesP1 += partie1(false);
        }
        System.out.println("Nombre de déplacement parties 1 : "+ totalMovesP1);
        System.out.println(" \nSimulation de "+ nbParties +" parties 2 :");
        int totalMovesP2 = 0;
        for(int i =0; i<nbParties ; i++){
            totalMovesP2 += partie2(false);
        }
        System.out.println("Nombre de déplacement parties 2 : "+ totalMovesP2);

    }
}