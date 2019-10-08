import java.util.ArrayList;

/**
 * Stocke les informations des seances des services
 */
public class Seance {
    private int codeSeance;
    private String dateEtHeure; //
    private double frais;
    private int codeProfessionnel;
    private int capacite;
    private ArrayList<Membre> listeParticipants;
    private int codeService;
    private String nomService;

    /**
     * Constructeur Seance
     * @param nomService nom du service
     * @param dateEtHeure date et heure de la seance
     * @param frais frais de la seance
     * @param codeProfessionnel code du professionnel
     * @param capacite capacite de la seance
     * @param codeService code du service
     * @param codeSeance code de la seance
     */
    public Seance(String nomService, String dateEtHeure, double frais,
                  int codeProfessionnel, int capacite, int codeService, int codeSeance) {
        this.nomService = nomService;
        this.codeSeance = genererCodeSeance(codeProfessionnel, codeService, codeSeance);
        this.dateEtHeure = dateEtHeure;
        this.frais = frais;
        this.codeProfessionnel = codeProfessionnel;
        this.capacite = capacite;
        this.listeParticipants = new ArrayList<>(capacite);
        this.codeService = codeService;
    }

    /**
     * Genere les codes de la seance selon les contraintes
     * @param codePro code du professionnel qui offre le service
     * @param codeService code du service
     * @param codeSeance code de la seance
     *
     * @return le code de la seance formatee
     */
    public int genererCodeSeance(int codePro, int codeService, int codeSeance) {
        if (codePro < 0 || codeSeance < 0 || codeService < 0){
            System.out.println("Erreur de format de code.");
            return -1;
        }
        String debut = Integer.toString(codeService / 10000);
        while(debut.length() < 3) {
            debut = "0" + debut;
        }

        String millieu = Integer.toString((codeSeance) % 100);
        while(millieu.length() < 2) {
            millieu = "0" + millieu;
        }

        String fin = Integer.toString(codePro % 100);
        while(fin.length() < 2) {
            fin = "0" + fin;
        }

        return Integer.parseInt((debut + millieu + fin));
    }

    /**
     * Rajoute des membres dans la liste de participants
     * @param membre Membre a ajouter
     */
    public void addParticipant(Membre membre) {
        for (int i = 0; i < listeParticipants.size(); i++) {
            if (membre.equals(listeParticipants.get(i))){
                System.out.println(membre.getNom()+" est deja inscrit(e) a cette activite.");
                return;
            }
        }
        listeParticipants.add(membre);
    }

    @Override
    public String toString(){
        return ("Service : " + nomService + "\n" +
                "Code service : " + codeService + "\n" +
                "Date et heure : " + dateEtHeure + "\n" +
                "Code de la seance : " + codeSeance + "\n");
    }

    /**
     * Affiche les inscriptions a la seance
     */
    public void afficherInscriptions(){
        System.out.println("\n" + this);
        System.out.println("Capacite totale : " + this.capacite);
        System.out.println("Nombre actuel d'inscriptions : " + this.listeParticipants.size() + "\n");
        for(Membre m : listeParticipants){
            System.out.println("Nom du membre : " + m.getNom());
            System.out.println("Numero du membre : " + m.getNumMembre());
            System.out.println("Courriel du membre : " + m.getCourriel() + "\n");
        }
    }

    //Getters
    public String getDateEtHeure() {
        return dateEtHeure;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getIdProfessionnel() {
        return codeProfessionnel;
    }

    public int getCodeService() {
        return codeService;
    }

    public double getFrais() {
        return frais;
    }

    public int getCodeProfessionnel() {
        return codeProfessionnel;
    }

    public String getNomService() {
        return nomService;
    }

    public ArrayList<Membre> getListeParticipants() {
        return listeParticipants;
    }

    public int getCodeSeance() {
        return codeSeance;
    }
}