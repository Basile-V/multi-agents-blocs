import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Environnement{

    private ArrayList<Stack<Agent>> map;
    Random ran = new Random();
    private Agent[] agents;
    private boolean success;
    private int nbMoves;
    private int maxMoves;
    private Lock intteraction = new ReentrantLock();
    private boolean verbose;

    public Environnement(int tailleMap, Agent[] agents, int maxMoves, boolean verbose){
        this.success = false;
        this.map = new ArrayList<>();
        for(int i=0; i < tailleMap; i++){
            this.map.add(new Stack(){
                @Override
                public synchronized boolean equals(Object o) {
                    return this == o;
                }
            });
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
        this.verbose = verbose;
    }

    public int deplacer(Agent a){
        int deplacement = 0;
        for(Stack<Agent> oldStack: this.map){
            if(oldStack.size() > 0 && oldStack.peek() == a){
                int rand = ran.nextInt(this.map.size()-1);
                ArrayList<Stack<Agent>> possibleMoves = (ArrayList<Stack<Agent>>) this.map.clone();
                possibleMoves.remove(oldStack);
                Stack<Agent> newStack = possibleMoves.get(rand);
                deplacement = map.indexOf(newStack) - map.indexOf(oldStack);
                handleDeplacement(a, newStack, oldStack);
                break;
            }
        }
        return deplacement;
    }

    public int deplacer(Agent a, int deplacement){
        for(Stack<Agent> oldStack: this.map){
            if(oldStack.size() > 0 && oldStack.peek() == a){
                Stack<Agent> newStack = this.map.get(this.map.indexOf(oldStack)+deplacement);
                handleDeplacement(a, newStack, oldStack);
                break;
            }
        }
        return deplacement;
    }

    /**
     *  L'agent se déplace mais pas du vecteur indiqué. Le déplacement final doit donc être une autre valeur.
     * @param a
     * @param deplacement
     * @return
     */
    public int deplacerSansDeplacement(Agent a, int deplacement){
        for(Stack<Agent> oldStack: this.map){
            if(oldStack.size() > 0 && oldStack.peek() == a){
                ArrayList<Stack<Agent>> possibleMoves = (ArrayList<Stack<Agent>>) this.map.clone();
                possibleMoves.remove(oldStack);
                Stack<Agent> stackEvite = this.map.get(this.map.indexOf(oldStack)+deplacement);
                possibleMoves.remove(stackEvite);
                int rand = ran.nextInt(this.map.size()-2);
                Stack<Agent> newStack = possibleMoves.get(rand);
                deplacement = map.indexOf(newStack) - map.indexOf(oldStack);
                handleDeplacement(a, newStack, oldStack);
                break;
            }
        }
        return deplacement;
    }

    public void handleDeplacement(Agent a, Stack<Agent> newStack, Stack<Agent> oldStack){
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
        print("Réussite : " + this.success +" | Nombre de déplacements : "+this.nbMoves);
        for (Agent a : agents){
            a.setFini(true);
        }
    }

    public int start(){
        for (Agent a : this.agents){
            a.start();
        }
        try {
            while(!this.success) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.nbMoves;
    }

    public void print(){
        if(!verbose) return;
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

    public synchronized void print(String s){
        if(verbose) System.out.println(s);
    }

    public void lock(){
        this.intteraction.lock();
    }

    public void unlock(){
        this.intteraction.unlock();
    }
}