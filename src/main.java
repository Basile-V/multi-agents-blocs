import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {

    public static Agent[] shuffleAgents(Agent[] agents){
        List<Agent> agentList = Arrays.asList(agents);
        Collections.shuffle(agentList);
        return agentList.toArray(agents);
    }

    public static int partie1(){
        Agent a = new Agent("A", null, null);
        Agent b = new Agent("B", a, null);
        Agent c = new Agent("C", b, null);
        Agent d = new Agent("D", c, null);
        Agent[] agents = shuffleAgents(new Agent[]{a, b, c, d});
        Environnement environnement = new Environnement(3, agents, 1000, false);
        for(Agent agent : agents){
            agent.setEnvironnement(environnement);
        }
        return environnement.start();
    }

    public static int partie2(){
        Agent a = new AgentCoordonne("A", null, null);
        Agent b = new AgentCoordonne("B", a, null);
        Agent c = new AgentCoordonne("C", b, null);
        Agent d = new AgentCoordonne("D", c, null);
        Agent[] agents = shuffleAgents(new Agent[]{a, b, c, d});
        Environnement environnement = new Environnement(3, agents, 1000, false);
        for(Agent agent : agents){
            agent.setEnvironnement(environnement);
        }
        return environnement.start();
    }


    public static void main(String[] args){
       /* System.out.println("Simulation de la partie 1: \n \n ");
        partie1();*/
        /*System.out.println(" \n \nSimulation de la partie 2: \n \n ");
        partie2();*/
        int totalMovesP1 = 0;
        for(int i =0; i<15; i++){
            totalMovesP1 += partie1();
        }
        System.out.println(" \n \nSimulation de la partie 2: \n \n ");
        int totalMovesP2 = 0;
        for(int i =0; i<15; i++){
            totalMovesP2 += partie1();
        }
        System.out.println("Parties 1 "+ totalMovesP1);
        System.out.println("Parties 2 "+ totalMovesP2);

    }
}