import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Stocke les informations des services offerts
 */
public class Service {
    private static int codeServiceIterateur = 1000000;
    private String nom;
    private String dateCreation;
    private String dateDebut;
    private String dateFin;
    private String heure;
    private ArrayList<String> recurrence;
    private int capacite;
    private int codeProfessionnel;
    private int codeService;
    private double frais;
    private String commentaire;
    private ArrayList<Seance> listeSeances = new ArrayList<>();

    /**
     * Constructeur service
     * @param nom nom du service
     * @param dateCreation date de la creation du service
     * @param dateDebut date du debut des seances
     * @param dateFin date de la fin des seances
     * @param heure heure des seances
     * @param recurrence recurrence des seances
     * @param codeProfessionnel code du professionnel offrant le service
     * @param capacite capacite maximale des seances
     * @param frais coût du service
     * @param commentaire commentaire du service (falcultatif)
     *
     * @throws ParseException Si les dates ne sont pas du bon format
     */
    public Service(String nom, String dateCreation, String dateDebut, String dateFin,
                   String heure, ArrayList<String> recurrence,
                   int codeProfessionnel, int capacite, double frais, String commentaire) throws ParseException {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heure = heure;
        this.recurrence = recurrence;
        this.capacite = capacite;
        this.codeProfessionnel = codeProfessionnel;
        this.codeService = codeServiceIterateur++;
        this.frais = frais;
        this.commentaire = commentaire;

        creerSeances(dateDebut, dateFin, heure, recurrence);
    }

    /**
     * Affiche toutes les seances pour ce service
     */
    public void afficherSeances() {
        System.out.println("Liste des seances pour ce service :");
        for (int i = 0; i < listeSeances.size(); i++) {
            Seance current = listeSeances.get(i);
            System.out.println(current + "\n");
        }
    }

    /**
     * Affiche toutes les seances ou il reste de la place pour inscription pour ce service
     */
    public void afficherSeancesDispo() {
        System.out.println("Liste des seances pour ce service :");
        for (int i = 0; i < listeSeances.size(); i++) {
            Seance current = listeSeances.get(i);
            if(current.getCapacite()>current.getListeParticipants().size()){
                System.out.println(current + "\n");
            }
        }
    }

    /**
     * Permet de creer des seances pour un service
     * @param dateDebutStr Date a compter de laquelle le service est offert
     * @param dateFinStr Date a compter de laquelle le service n'est plus offert
     * @param heure Heure a laquelle le service est offert
     * @param joursOfferts Jours pendant lesquels le service est offert
     * @throws ParseException lorsque la variable qu'on parse n'est pas du bon format
     */
    public void creerSeances(String dateDebutStr, String dateFinStr,
                             String heure, ArrayList joursOfferts) throws ParseException {
        int codeSeance = 0;
        SimpleDateFormat sdf = Systeme.getSimpleDateFormat();
        Date dateDebut = (new SimpleDateFormat(Systeme.getSimpleDateFormat().toPattern())).parse(dateDebutStr);
        Date dateFin = (new SimpleDateFormat(Systeme.getSimpleDateFormat().toPattern())).parse(dateFinStr);
        Calendar c = Calendar.getInstance();
        c.setTime(dateDebut);
        int[] joursInt = getJoursIntFromJoursStr(joursOfferts);

        // On verifie chaque date entre la date de debut et la date de fin. Si le jour de la semaine
        // correspond a ceux prevus, on cree une seance
        for (Date i = dateDebut; i.before(dateFin); i = c.getTime()) {
            int jourSemaine = c.get(Calendar.DAY_OF_WEEK);
            for (int j = 0; j < joursInt.length; j++) {
                if (jourSemaine == joursInt[j]) {
                    String date = sdf.format(i);
                    String dateEtHeure = "" + date + " " + heure;
                    Seance s = new Seance(this.nom, dateEtHeure, this.frais, this.codeProfessionnel,
                                          this.capacite, this.codeService, codeSeance++);
                    if (codeSeance > 100) {
                        System.out.println("Nombre de seances trop eleve, seules les 100 premieres ont ete creees");
                        System.out.println("Veuillez creer un nouveau service");
                        return;
                    }
                    listeSeances.add(s);
                }
            }
            c.add(Calendar.DATE, 1);
        }
    }

    /**
     * Permet de traduire une liste de jours en francais en leur code numerique (dimanche = 1, etc.)
     * @param joursArray ArrayList des jours en format String
     * @return Tableau de chiffres correspondants aux jours
     */
    private int[] getJoursIntFromJoursStr(ArrayList joursArray){
        int[] joursIntArray = new int[joursArray.size()];
        for (int i = 0; i < joursArray.size(); i++) {
            String jour = (String)joursArray.get(i);
            switch (jour.toLowerCase()){
                case "dimanche":{
                    joursIntArray[i]=1;
                    break;
                }
                case "lundi":{
                    joursIntArray[i]=2;
                    break;
                }
                case "mardi":{
                    joursIntArray[i]=3;
                    break;
                }
                case "mercredi":{
                    joursIntArray[i]=4;
                    break;
                }
                case "jeudi":{
                    joursIntArray[i]=5;
                    break;
                }
                case "vendredi":{
                    joursIntArray[i]=6;
                    break;
                }
                case "samedi":{
                    joursIntArray[i]=7;
                    break;
                }
            }
        }
        return joursIntArray;
    }

    @Override
    public String toString(){
        return ("Service : " + nom + "\n" +
                "Code service : " + codeService + "\n");
    }

    //Getters
    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getHeure() {
        return heure;
    }

    public ArrayList<String> getRecurrence() {
        return recurrence;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getCodeProfessionnel() {
        return codeProfessionnel;
    }

    public int getCodeService() {
        return codeService;
    }

    public String getNom() {
        return nom;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public double getFrais() {
        return frais;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public ArrayList<Seance> getListeSeances() {
        return listeSeances;
    }

    public Seance getSeance(int numSeance){
        Seance seanceChosie = null;
        for (Seance seance : listeSeances ){
            if (seance.getCodeSeance() == numSeance){
                seanceChosie = seance;
            }
        }
        return seanceChosie;
    }

    //Setters
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setRecurrence(ArrayList<String> recurrence) {
        this.recurrence = recurrence;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setCodeProfessionnel(int codeProfessionnel) {
        this.codeProfessionnel = codeProfessionnel;
    }

    public void setCodeService(int codeService) {
        this.codeService = codeService;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setListeSeances(ArrayList<Seance> listeSeances) {
        this.listeSeances = listeSeances;
    }

}