import java.util.Random;

public class Agent extends Thread{
    private String numero;
    private Agent voisinDessus;
    private Agent voisinDessous;
    private Agent objectif;
    private boolean isPushed;
    private Environnement environnement;
    private boolean fini = false;

    public Agent(String numero, Agent objectif, Environnement environnement) {
        this.numero = numero;
        this.objectif = objectif;
        this.environnement = environnement;
    }

    @Override
    public void run(){
        System.out.println("Agent "+ this.numero + "start running");
        while(!fini){
            try {
                if(!objectifAtteint() || this.isPushed) {
                    action();
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    public void seDeplacer(){
        if(this.getVoisinDessus() == null){
            this.environnement.deplacer(this);
        }
    }

    private Agent getVoisinDessus() {
        return voisinDessus;
    }

    public void seFaisPousser(){
        this.isPushed = true;
    }

    public void pousse(){
        this.voisinDessus.seFaisPousser();
    }

    public void setVoisinDessus(Agent voisinDessus) {
        this.voisinDessus = voisinDessus;
    }

    public void setVoisinDessous(Agent voisinDessous) {
        this.voisinDessous = voisinDessous;
    }

    private synchronized void action(){
        synchronized (environnement){
            if (this.voisinDessus != null) {
                    System.out.println("L'agent " + this.numero + " pousse");
                    this.pousse();
            } else {
                System.out.println("L'agent " + this.numero + " se déplace");
                this.seDeplacer();
            }
        }
    }

    public boolean objectifAtteint(){
        return this.objectif == this.voisinDessous;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    public void setPushed(boolean pushed) {
        isPushed = pushed;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }

    @Override
    public String toString() {
        return numero;
    }
}