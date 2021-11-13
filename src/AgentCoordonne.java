public class AgentCoordonne extends Agent{
    private int dernierDeplacement;

    public AgentCoordonne(String numero, Agent objectif, Environnement environnement) {
        super(numero, objectif, environnement);
    }

    @Override
    protected void pousse(){
        if(this.isObjectifDessus()) pousseEtSuit();
        else pousseEtNeSuitPas();
    }

    private void pousseEtSuit() {
        environnement.print("et suit");
        AgentCoordonne meneur = (AgentCoordonne) this.voisinDessus;
        meneur.seFaisPousser();
        environnement.unlock();
        try {
            while (meneur == this.voisinDessus) Thread.sleep(5);
            environnement.lock();
            environnement.print("L'agent "+numero+" a suivit");
            environnement.deplacer(this, meneur.getDernierDeplacement());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pousseEtNeSuitPas() {
        environnement.print("et ne suit pas");
        AgentCoordonne antiMeneur = (AgentCoordonne) this.voisinDessus;
        antiMeneur.seFaisPousser();
        environnement.unlock();
        try {
            while (antiMeneur == this.voisinDessus) Thread.sleep(5);
            environnement.lock();
            environnement.print("L'agent "+numero+" n'a pas suivi");
            environnement.deplacerSansDeplacement(this, antiMeneur.getDernierDeplacement());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void seDeplacer(){
        this.dernierDeplacement = this.environnement.deplacer(this);
    }

    public int getDernierDeplacement() {
        return dernierDeplacement;
    }

    public boolean isObjectifDessus(){
        return this.voisinDessus == this.objectif;
    }
}
