import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Environnement{

    private ArrayList<Stack<Agent>> map;
    Random ran = new Random();
    private Agent[] agents;
    private boolean success;
    private int nbMoves;
    private int maxMoves;

    public Environnement(int tailleMap, Agent[] agents, int maxMoves){
        this.success = false;
        this.map = new ArrayList<>();
        for(int i=0; i < tailleMap; i++){
            this.map.add(new Stack());
        }
        for(Agent a: agents) {
            if(!this.map.get(0).isEmpty()) {
                a.setVoisinDessous(this.map.get(0).peek());
                this.map.get(0).peek().setVoisinDessus(a);
            }
            this.map.get(0).push(a);
        }
        this.agents = agents;
        this.nbMoves = 0;
        this.maxMoves = maxMoves;
    }

    public synchronized void deplacer(Agent a){
        for(Stack<Agent> oldStack: this.map){
            if(oldStack.size() > 0 && oldStack.peek() == a){
                int rand = ran.nextInt(this.map.size()-1);
                ArrayList<Stack<Agent>> possibleMoves = (ArrayList<Stack<Agent>>) this.map.clone();
                possibleMoves.remove(oldStack);
                Stack<Agent> newStack = possibleMoves.get(rand);
                if(newStack.size() != 0) {
                    Agent voisinDessous = newStack.peek();
                    newStack.push(oldStack.pop());
                    a.setVoisinDessous(voisinDessous);
                    voisinDessous.setVoisinDessus(a);
                }else{
                    newStack.push(oldStack.pop());
                    a.setVoisinDessous(null);
                }
                if(oldStack.size() > 0 ) oldStack.peek().setVoisinDessus(null);
                a.setPushed(false);
                this.nbMoves++;
                if(reussite() || this.nbMoves >= maxMoves) terminerLeProgramme();
                break;
            }
        }
        print();
    }

    public boolean reussite(){
        for(Agent a: agents){
            if(!a.objectifAtteint()) return false;
        }
        this.success = true;
        return true;
    }

    public void terminerLeProgramme(){
        System.out.println("Réussite : " + this.success +" | Nombre de déplacements : "+this.nbMoves);
        for (Agent a : agents){
            a.setFini(true);
        }
        //Kill threads
    }

    public void start(){
        for (Agent a : this.agents){
            a.start();
        }
    }

    public void print(){
        ArrayList<Stack<Agent>> stacksConsumer = new ArrayList<>();
        for(Stack<Agent> stack : this.map) stacksConsumer.add((Stack<Agent>) stack.clone());
        for(int i = agents.length; i>0 ; i--){
            String line = "";
            for(Stack<Agent> stack: stacksConsumer){
                if(stack.size() == i) line += "|"+stack.pop()+"| ";
                else line += "    ";
            }
            System.out.println(line);
        }
    }
}