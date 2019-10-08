import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * Compte professionnel utilise pour stocker les informations des professionnels d'#GYM
 */
public class Professionnel extends Usager{
    private static int nextNumPro = 100000000;
    private int numPro;
    private double montantDu;
    private LinkedList<Service> services;

    /**
     * Constructeur pour professionnel
     * @param nom nom du professionnel
     * @param courriel le courriel du professionnel (format xxx@xxx.xxx)
     * @param adresse l'adresse du professionnel (25 char. max)
     * @param ville ville de residence du professionnel (25 char. max)
     * @param province province de residence du professionnel (format Qc, On, etc.)
     * @param codePostal code postal du professionnel (format H0H 0H0)
     */
    public Professionnel(String nom, String courriel, String adresse,
                         String ville, String province, String codePostal) {
        super(nom, courriel, adresse, ville, province, codePostal);
        this.numPro = nextNumPro++;
        this.montantDu = 0;
        this.services = new LinkedList<>();
    }

    /**
     * Met a jour le montant dû au membre pour les services ayant eu lieu cette semaine
     * @return montant dû au professionnel
     */
    public double updateMontantDu() {
        Date aujourdhui = Calendar.getInstance().getTime();
        String aujourdhuiStr = Systeme.getSimpleDateFormat().format(aujourdhui);
        double montantTotalDu = 0;

        for (Service service : services) {
            for (Seance seance : service.getListeSeances()) {
                String dateHeure = seance.getDateEtHeure();
                String[] dateSplitHeure = dateHeure.split(" ");

                String[] infosDate = dateSplitHeure[0].split("-");
                int an = Integer.parseInt(infosDate[2]) - 1900;
                int mois = Integer.parseInt(infosDate[1]) - 1;
                int jour = Integer.parseInt(infosDate[0]);

                Date date = Systeme.stringToDate(seance.getDateEtHeure());
                String dateStr = Systeme.getSimpleDateFormat().format(date);

                if (date.compareTo(aujourdhui) < 0) {
                    for (Membre m : seance.getListeParticipants()) {
                        montantTotalDu += seance.getFrais();
                    }
                }
            }
        }
        this.setMontantDu(montantTotalDu);
        return montantTotalDu;
    }

    /**
     * Calcule le nombre de services ayant eu lieu cette semaine
     * @return le nombre de services ayant eu lieu cette semaine
     */
    public int getNombreSeancesSem(){
        Date aujourdhui = Calendar.getInstance().getTime();
        int nombreSeances = 0;
        for(Service serv : services){
            for(Seance seance : serv.getListeSeances()){
                Date date = Systeme.stringToDate(seance.getDateEtHeure());
                if(date.compareTo(aujourdhui) < 0){
                    nombreSeances++;
                }
            }
        }
        return nombreSeances;
    }

    //Getters
    public int getNumPro() {
        return numPro;
    }

    public double getMontantDu() {
        return montantDu;
    }

    public LinkedList<Service> getServices() {
        return services;
    }

    //Setters
    public void setMontantDu(double montantDu) {
        this.montantDu = montantDu;
    }
}
