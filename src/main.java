public class main {

    public static void main(String[] args){
        Agent a = new Agent("A", null, null);
        Agent b = new Agent("B", a, null);
        Agent c = new Agent("C", b, null);
        Agent d = new Agent("D", c, null);
        Agent[] agents = {b, d, c, a};
        Environnement environnement = new Environnement(3, agents, 1000);
        for(Agent agent : agents){
            agent.setEnvironnement(environnement);
        }
        environnement.start();
    }
}