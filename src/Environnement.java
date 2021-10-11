import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Environnement extends Thread{

    private ArrayList<Stack<Agent>> map;
    private Agent[] agents;
    private boolean fini;
    private int nbMoves;
    private int maxMoves;

    public Environnement(int tailleMap, Agent[] agents, int maxMoves){
        this.fini = false;
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

    public boolean peutSeDeplacer(Agent a){
        int i = 0;
        while(i < this.map.size()){
            if(this.map.get(i++).peek() == a)  return true;
        }
        return false;
    }

    public void deplacer(Agent a){
        int i = 0;
        while(i < this.map.size()){
            if(this.map.get(i).peek() == a){
                Random ran = new Random();
                int x = ran.nextInt(this.map.size());
                while(x == i){
                    x = ran.nextInt(this.map.size());
                }
                Agent voisinDessous = this.map.get(x).peek();
                this.map.get(x).push(this.map.get(i).pop());
                a.setVoisinDessous(voisinDessous);
                this.map.get(i).peek().setVoisinDessous(null);
                this.nbMoves++;
                this.map.get(x).peek().setPushed(false);
                if(reussite() || this.nbMoves >= maxMoves) terminerLeProgramme();
                return;
            }
            i++;
        }
        print();
    }

    public boolean reussite(){
        for(Agent a: agents){
            if(!a.objectifAtteint()) return false;
        }
        this.fini = true;
        return true;
    }

    public void terminerLeProgramme(){
        System.out.println("Réussite :" + this.fini +" nombre de déplacements :"+this.nbMoves);
        for (Agent a : agents){
            a.interrupt();
        }
        this.interrupt();
        //Kill threads
    }

    @Override
    public void run(){
        for (Agent a : this.agents){
            a.run();
        }
        while(!this.fini){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    public void print(){
        ArrayList<ArrayList<Agent>> map = new ArrayList<ArrayList<Agent>>();
        for (int i = 0; i < this.map.size(); i++){
            map.set(i, new ArrayList(this.map.get(i)));
        }
        System.out.println("----------------------");
        for(int y = this.agents.length - 1; y >= 0; y++){
            String line = "|";
            for(int x = 0; x < map.size(); x++){
                if(map.get(x).size() < y){
                    line += "x|";
                }
                else{
                    line += map.get(x).get(y).getNumero() + "|";
                }
            }
            System.out.println(line);
        }
        System.out.println();
    }
}