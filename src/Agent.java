import java.util.Random;

public class Agent extends Thread{
    private String numero;
    private Agent voisinDessus;
    private Agent voisinDessous;
    private Agent objectif;
    private boolean isPushed;
    private Environnement environnement;

    public Agent(String numero, Agent objectif, Environnement environnement) {
        this.numero = numero;
        this.voisinDessus = voisinDessus;
        this.voisinDessous = voisinDessous;
        this.objectif = objectif;
        this.environnement = environnement;
    }

    @Override
    public void run(){
        while(true){
            try {
                action();
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

    public String getNumero() {
        return numero;
    }

    private Agent getVoisinDessus() {
        return voisinDessus;
    }

    private Agent getVoisinDessous() {
        return voisinDessous;
    }

    private Agent getObjectif() {
        return objectif;
    }

    public Environnement getEnvironnement() {
        return environnement;
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

    private void action(){
        if(!objectifAtteint() || this.isPushed){
            if(this.getVoisinDessus() != null) {
                System.out.println("L'agent " + this.numero + " pousse");
                this.pousse();
            }else{
                System.out.println("L'agent " + this.numero + " se déplace");
                this.seDeplacer();
            }
        }
    }

    public boolean objectifAtteint(){
        return this.objectif == this.getVoisinDessous();
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    public void setPushed(boolean pushed) {
        isPushed = pushed;
    }
}