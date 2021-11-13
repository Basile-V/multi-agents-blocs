public class Agent extends Thread{
    protected String numero;
    protected Agent voisinDessus;
    protected Agent voisinDessous;
    protected Agent objectif;
    protected boolean isPushed;
    protected Environnement environnement;
    protected boolean fini = false;

    public Agent(String numero, Agent objectif, Environnement environnement) {
        this.numero = numero;
        this.objectif = objectif;
        this.environnement = environnement;
    }

    @Override
    public void run(){
        environnement.print("Agent "+ this.numero + "start running");
        while(!fini){
            try {
                if(!objectifAtteint() || this.isPushed) {
                    action();
                }
                Thread.sleep(100);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    protected void seDeplacer(){
        this.environnement.deplacer(this);
    }

    protected void seFaisPousser(){
        this.isPushed = true;
    }

    protected void pousse(){
        this.voisinDessus.seFaisPousser();
    }

    public void setVoisinDessus(Agent voisinDessus) {
        this.voisinDessus = voisinDessus;
    }

    public void setVoisinDessous(Agent voisinDessous) {
        this.voisinDessous = voisinDessous;
    }

    private void action(){
        environnement.lock();
        if (this.voisinDessus != null) {
            environnement.print("L'agent " + this.numero + " pousse");
            this.pousse();
        } else {
            environnement.print("L'agent " + this.numero + " se déplace");
            this.seDeplacer();
        }
        environnement.unlock();
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