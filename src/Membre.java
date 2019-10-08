import java.util.ArrayList;

/**
 * Compte membre utilise pour stocker les informations des membres de #GYM
 */
public class Membre extends Usager{
    private static int nextNumMembre = 100000000;
    private boolean valide;
    private int numMembre;
    private ArrayList<Seance> incriptionsSeances = new ArrayList<>();

    // HYPOTHeSE : Le membre paie son premier mois a la creation du compte
    /**
     * Constructeur pour Membre
     * @param nom le nom du membre
     * @param courriel le courriel du membre (format xxx@xxx.xxx)
     * @param adresse l'adresse du membre (25 characteres max.)
     * @param ville nom de la ville de residence du membre (25 characteres max.)
     * @param province province de residence du membre (format Qc, On, etc.)
     * @param codePostal code postal du membre (format H0H 0H0)
     */
    public Membre (String nom, String courriel, String adresse, String ville, String province, String codePostal) {
        super(nom, courriel, adresse, ville, province, codePostal);
        this.valide = true;
        this.numMembre = nextNumMembre++;
    }

    /**
     * Affiche les seances dans lesquels le membre est inscrit
     */
    public void afficherSeances(){
        for(Seance seance : incriptionsSeances){
            System.out.println(seance);
        }
    }

    // Getters
    public boolean isValide() {
        return valide;
    }

    public int getNumMembre() {
        return numMembre;
    }

    public ArrayList<Seance> getIncriptionsSeances() {
        return incriptionsSeances;
    }

    // Setters
    public void addInscriptions(Seance seance){
        incriptionsSeances.add(seance);
    }
}