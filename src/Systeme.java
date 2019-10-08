import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Systeme/Serveur de #Gym, enregistre la plupart des informations reliees aux operations de #Gym
 */
public class Systeme {
    private static TreeMap<String, String> courrielToUsager = new TreeMap<>();
    private static TreeMap<Integer, Membre> serverMembre = new TreeMap<>();
    private static TreeMap<Integer, Professionnel> serverPro = new TreeMap<>();
    private static TreeMap<Integer, Service> serverService = new TreeMap<>();
    private static Date dateActuelle = Calendar.getInstance().getTime();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat dateEtHeureFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

    /**
     * Verifie la validite d'un membre
     * @param numMembreInt Numero lie au compte du membre
     * @return Retourne 1 si membre valide, 0 si existant mais suspendu et -1 si invalide
     */
    public static int verifierMembre(int numMembreInt) {
        Membre membre = serverMembre.get(numMembreInt);

        if (membre != null) {
            if (membre.isValide()) {
                System.out.println("\nValide");
                System.out.println("Nom : " + membre.getNom());
                System.out.println("Numero de membre : " + membre.getNumMembre());
                return 1;
            } else {
                System.out.println("\nMembre suspendu");
                return 0;
            }
        }
        System.out.println("\nNumero invalide");
        return -1;
    }

    /**
     * Verifie la validite du compte professionnel
     * @param numProInt numero du code du professionnel
     * @return true si valide, false si invalide
     */
    public static boolean verifierPro(int numProInt) {
        Professionnel pro = serverPro.get(numProInt);

        if (pro != null) {
            System.out.println("\nNom : " + pro.getNom());
            System.out.println("Numero de professionnel : " + pro.getNumPro());
            return true;
        } else {
            System.out.println("Numero de professionnel invalide.");
            return false;
        }
    }

    /**
     * Verifie la validite d'un membre.
     * @param courriel Numero lie au compte du membre
     */
    public static void verifierCourriel(String courriel) {
        String numUsagerStr = courrielToUsager.get(courriel.toUpperCase());

        if (numUsagerStr != null) {
            char typeUsager = numUsagerStr.charAt(0);
            int numUsager = Integer.parseInt(numUsagerStr.substring(1));

            if (typeUsager == 'P') {
                verifierPro(numUsager);
            } else if (typeUsager == 'M') {
                verifierMembre(numUsager);
            } else {
                System.out.println("Erreur du programme. Formattage membre/professionnel : courrielToUsager");
            }
        } else {
            System.out.println("Courriel inexistant");
        }
    }

    /**
     * Ajoute un compte membre au serveur membre
     * @param compte Compte membre a ajouter
     */
    public static void ajouterCompteMembre(Membre compte) {
        int numMembre = compte.getNumMembre();
        courrielToUsager.put(compte.getCourriel().toUpperCase(), "M" + numMembre);
        serverMembre.put(numMembre, compte);
    }

    /**
     * Ajoute un compte professionnel au serveur professionnel
     * @param compte Compte professionnel a ajouter
     */
    public static void ajouterComptePro(Professionnel compte) {
        int numPro = compte.getNumPro();
        courrielToUsager.put(compte.getCourriel().toUpperCase(), "P" + numPro);
        serverPro.put(numPro, compte);
    }

    /**
     * Ajoute un service au serveur Service
     * @param service le service a ajouter
     * @param idProfessionnel numero du professionnel
     */
    public static void ajouterService(Service service, int idProfessionnel) {
        // Ajout du service au compte du professionnel
        serverPro.get(idProfessionnel).getServices().add(service);

        // Ajout du service sur le serveur #GYM
        int numService = service.getCodeService();
        serverService.put(numService, service);
    }

    /**
     * Supprime un membre du systeme
     * @param numMembre le numero du membre a supprimer
     */
    public static void supprimerMembre(int numMembre){
        serverMembre.remove(numMembre);
    }

    /**
     * Supprime un professionnel du systeme
     * @param numPro le numero du professionnel a supprimer
     */
    public static void supprimerPro(int numPro){
        serverPro.remove(numPro);
    }

    /**
     * Supprime un service (et les seances associees) du systeme
     * @param numService le numero du service a supprimer
     */
    public static void supprimerService(int numService){
        serverService.remove(numService);
    }

    /**
     * Affiche la liste de toutes les seances
     */
    public static void afficherListeSeances(){
        for(Map.Entry<Integer, Service> entry : serverService.entrySet()){
            Service service = entry.getValue();
            service.afficherSeancesDispo();
        }
    }

    /**
     * Permet a un professionnel de consulter les seances qu'il offre
     * @param numPro le numero du professionnel qui veut consulter sa liste de seances
     */
    public static void consulterSeances(int numPro){
        for(Map.Entry<Integer, Service> entry : serverService.entrySet()){
            Service service = entry.getValue();
            if(numPro == service.getCodeProfessionnel()){
                service.afficherSeances();
            }
        }
    }

    /**
     * Permet de consulter les inscriptions a une seance
     * @param seance la seance dont on veut consulter les inscriptions
     */
    public static void consulterInscriptions(Seance seance){
        seance.afficherInscriptions();
    }

    /**
     * Permet a un membre de confirmer sa presence a une seance
     * @param seance la seance a laquelle le membre s'est inscrit
     * @param membre le membre dont on veut confirmer la presence
     * @return true si l'inscription est reussie, false sinon
     */
    public static boolean confirmerInscription(Seance seance, Membre membre){
        for(Membre m : seance.getListeParticipants()){
            if(m.getNumMembre() == membre.getNumMembre()){
                Date dateHeureActuelles = Calendar.getInstance().getTime();
                File file = new File(
                        "enregistrements/CONF" +
                                seance.getCodeSeance() + "_" +
                                membre.getNumMembre() + ".txt");
                file.getParentFile().mkdirs();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(
                            "Date et heure actuelles: " + dateHeureActuelles.toString() + "\n" +
                                    "Numero du professionnel: " + seance.getCodeProfessionnel() + "\n" +
                                    "Numero du membre: " + membre.getNumMembre() + "\n" +
                                    "Code du service: " + seance.getCodeService() + "\n" +
                                    "Code de la seance: " + seance.getCodeSeance() + "\n"
                    );
                    writer.close();
                    return true;
                } catch (IOException e){
                    System.out.println("Erreur d'ecriture d'enregistrement pour les confirmations");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Active le processus comptable :
     * Genere le fichier TEF, les factures et les avis de paiement
     * Envoit le rapport de synthese hebdomadaire au gerant
     */
    public static void processusComptable(){
        System.out.println("\nPROCESSUS COMPTABLE (SIMULATION)");
        System.out.println("\nContenu du rapport de synthese hebdomadaire :");
        genererRapportComptable();
        genererTEF();
        genererAvisPaiement();
        System.out.println("Avis de paiement des professionnels generes");
        genererFactures();
        System.out.println("Factures des membres generees");

    }

    /**
     * Genere un fichier .tef dans le dossier "factures" qui permet de payer les professionnels
     */
    public static void genererTEF(){
        BufferedWriter writer;
        Date aujourdhui = Calendar.getInstance().getTime();
        String aujourdhuiStr = simpleDateFormat.format(aujourdhui);
        DecimalFormat df2 = new DecimalFormat("#.00");
        File file = new File("factures/TEF" + "_" + aujourdhuiStr + ".tef");
        try{
            new FileReader(file).close();
            System.out.println("Fichier TEF hebdomadaire deja genere");
            return;
        } catch(IOException eRead) {
            try {
                file.getParentFile().mkdirs();
                writer = new BufferedWriter(new FileWriter(file));

                for (Map.Entry<Integer, Professionnel> entry : serverPro.entrySet()) {
                    Professionnel pro = entry.getValue();
                    if (pro.getMontantDu() != 0) {
                        writer.write("Nom : " + pro.getNom() + "\n");
                        writer.write("Numero du professionnel : " + pro.getNumPro() + "\n");
                        writer.write("Montant dû : " + df2.format(pro.getMontantDu()) + "$\n");
                        writer.write("------\n");
                    }
                }
                writer.close();
                System.out.println("Fichier TEF genere");
            } catch (IOException eWrite) {
                System.out.println("Erreur dans l'ecriture de fichier TEF");
            }
        }

    }

    /**
     *
     * Genere un rapport de synthese hebdomadaire sur demande.
     * Le rapport est normalement envoye au gerant (il est ici imprime sur la console pour simuler l'envoi)
     */
    public static void genererRapportComptable(){
        int nbProActifs = 0;
        double montantTotalDu = 0.0;
        int nbTotalServices = 0;
        for(Map.Entry<Integer, Professionnel> entry : serverPro.entrySet()){
            Professionnel pro = entry.getValue();
            if (pro.updateMontantDu() != 0) {
                nbProActifs++;
                montantTotalDu += pro.getMontantDu();
                nbTotalServices += pro.getNombreSeancesSem();

                System.out.println(
                        "Nom du professionnel : " + pro.getNom() + "\n" +
                                "Nombre de services : " + pro.getNombreSeancesSem() + "\n" +
                                "Montant du : " + String.format("%.2f", pro.getMontantDu()) + "$\n"
                );
            }
        }
        System.out.println(
                "Nombre total de professionnels ayant fourni un service: " + nbProActifs + "\n" +
                        "Nombre total de services : " + nbTotalServices + "\n" +
                        "Montant total du : " + String.format("%.2f", montantTotalDu) + "$\n"
        );
    }


    /**
     * Genere les factures aux Membres qui ont des montants a acquitter en .txt dans le dossier "factures"
     */
    public static void genererFactures(){
        Date aujourdhui = Calendar.getInstance().getTime();
        String aujourdhuiStr = dateEtHeureFormat.format(aujourdhui);
        DecimalFormat df2 = new DecimalFormat("0.00");
        try {
            for (Map.Entry<Integer, Membre> entry : serverMembre.entrySet()) {
                Membre membre = entry.getValue();
                double montantDu = 0;
                ArrayList<Seance> seancesMembre = membre.getIncriptionsSeances();
                for (int i = 0; i < seancesMembre.size(); i++) {
                    Date dateSeance = stringToDate(seancesMembre.get(i).getDateEtHeure());
                    if(dateSeance.compareTo(aujourdhui) < 0) {
                        montantDu += membre.getIncriptionsSeances().get(i).getFrais();
                    }
                }
                if (montantDu > 0) {
                    int montantTotal = 0;
                    BufferedWriter writer;
                    File file = new File("factures/" + membre.getNom().replace(" ", "_")
                            + "_" + aujourdhuiStr + ".txt");
                    file.getParentFile().mkdirs();
                    writer = new BufferedWriter(new FileWriter(file));

                    // Afficher infos sur le membre
                    writer.write("Nom du membre : " + membre.getNom());
                    writer.write("\nNumero du membre : " + membre.getNumMembre());
                    writer.write("\nAdresse : " + membre.getAdresse());
                    writer.write("\nVille : " + membre.getVille());
                    writer.write("\nProvince : " + membre.getProvince());
                    writer.write("\nCode postal : " + membre.getCodePostal());

                    // Affichage infos sur les seances du membre
                    Stack<Seance> seancesRetirer = new Stack<>();

                    for (Seance s : seancesMembre) {
                        Date date = stringToDate(s.getDateEtHeure());
                        String dateStr = simpleDateFormat.format(date);

                        if (date.compareTo(aujourdhui) < 0) {
                            Professionnel pro = serverPro.get(s.getIdProfessionnel());
                            seancesRetirer.add(s);
                            writer.newLine();
                            writer.write("\nDate du service : " + dateStr);
                            writer.write("\nNom du professionnel : " + pro.getNom());
                            writer.write("\nNom du service : " + s.getNomService());
                            writer.write("\nCoût du service : " + df2.format(s.getFrais()) + "$");
                            montantTotal += s.getFrais();
                        }
                    }
                    writer.write("\n\nMontant total dû : " + df2.format(montantTotal) + "$");
                    writer.close();

                    while (!seancesRetirer.isEmpty()) {
                        seancesMembre.remove(seancesRetirer.pop());
                    }
                }
            }
        } catch(IOException e){
            System.out.println("Erreur dans la creation de fichiers pour facture.");
            System.out.println("Contactez un administrateur systeme.");
        }
    }

    /**
     * Genere les avis de paiement des professionnels en .txt dans le dossier "factures"
     */
    public static void genererAvisPaiement(){
        Date aujourdhui = Calendar.getInstance().getTime();
        String aujourdhuiStr = dateEtHeureFormat.format(aujourdhui);
        DecimalFormat df2 = new DecimalFormat("0.00");
        try {
            for (Map.Entry<Integer, Professionnel> entry : serverPro.entrySet()) {
                Professionnel pro = entry.getValue();
                if (pro.getMontantDu() > 0){
                    int montantTotal = 0;
                    BufferedWriter writer;
                    File file = new File(
                            "factures/" + pro.getNom().replace(" ", "_")
                                    + "_" + aujourdhuiStr + ".txt");
                    file.getParentFile().mkdirs();
                    writer = new BufferedWriter(new FileWriter(file));

                    // Afficher infos sur le pro
                    writer.write("Nom du pro : " + pro.getNom());
                    writer.write("\nNumero du pro : " + pro.getNumPro());
                    writer.write("\nAdresse : " + pro.getAdresse());
                    writer.write("\nVille : " + pro.getVille());
                    writer.write("\nProvince : " + pro.getProvince());
                    writer.write("\nCode postal : " + pro.getCodePostal());

                    // Affichage infos sur les seances du pro
                    LinkedList<Service> services = pro.getServices();
                    Stack<Seance> seancesRetirer = new Stack<>();
                    Stack<Service> serviceRetirer = new Stack<>();

                    for (Service service : services) {
                        for (Seance seance : service.getListeSeances()) {
                            Date date = stringToDate(seance.getDateEtHeure());
                            String dateStr = simpleDateFormat.format(date);

                            if (date.compareTo(aujourdhui) < 0) {
                                for (Membre m : seance.getListeParticipants()) {
                                    seancesRetirer.add(seance);
                                    writer.newLine();
                                    writer.write("\nDate du service : " + dateStr);
                                    writer.write("\nDate d'enregistrement : " + service.getDateCreation());
                                    writer.write("\nNom du Membre : " + m.getNom());
                                    writer.write("\nCode du service : " + service.getCodeService());
                                    writer.write("\nCode de la seance : " + seance.getCodeSeance());
                                    writer.write("\nCoût du service : " + df2.format(seance.getFrais()) + "$");
                                    writer.newLine();
                                    montantTotal += seance.getFrais();
                                    pro.setMontantDu((pro.getMontantDu() - montantTotal));
                                }
                            }
                        }

                        while (!seancesRetirer.isEmpty()) {
                            service.getListeSeances().remove(seancesRetirer.pop());
                        }

                        if (service.getListeSeances().isEmpty()) {
                            serviceRetirer.add(service);
                        }
                    }

                    while (!serviceRetirer.isEmpty()) {
                        Systeme.supprimerService(serviceRetirer.pop().getCodeService());
                    }
                    writer.write("\n\nMontant total : " + df2.format(montantTotal) + "$");
                    writer.close();
                }
            }

        } catch (IOException e){
            System.out.println("Erreur dans la creation de fichiers pour avis de paiement.");
            System.out.println("Contactez un administrateur systeme.");
        }
    }

    /**
     * Inscrit un membre a une seance et met a jour le montant dû au professionnel
     * @param seance seance ou le membre s'inscrit
     * @param membre membre a inscrire
     */
    public static void inscrireMembre(Seance seance, Membre membre) {
        Professionnel pro = serverPro.get(seance.getIdProfessionnel());
        pro.setMontantDu(pro.getMontantDu() + seance.getFrais());

        seance.addParticipant(membre);
        enregistrementInscription(seance, membre);
        membre.addInscriptions(seance);
    }

    /**
     * Cree un enregistrement de l'inscription d'un membre a une seance
     * @param seance seance auquel le membre s'inscrit
     * @param membre membre inscrit
     */
    public static void enregistrementInscription(Seance seance, Membre membre){
        for(Membre m : seance.getListeParticipants()){
            if(m.getNumMembre() == membre.getNumMembre()){
                Date dateHeureActuelles = Calendar.getInstance().getTime();
                File file = new File(
                        "enregistrements/INSC" +
                                seance.getCodeSeance() + "_" +
                                membre.getNumMembre() + ".txt");
                file.getParentFile().mkdirs();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(
                            "Date et heure actuelles: " + dateHeureActuelles.toString() + "\n" +
                                    "Date a laquelle le service sera fourni: " + seance.getDateEtHeure().split(" ")[0] + "\n" +
                                    "Numero du professionnel: " + seance.getCodeProfessionnel() + "\n" +
                                    "Numero du membre: " + membre.getNumMembre() + "\n" +
                                    "Code du service: " + seance.getCodeService() + "\n" +
                                    "Code de la seance: " + seance.getCodeSeance() + "\n" +
                                    "Commentaires: " + "\n"
                    );
                    writer.close();
                } catch (IOException e){
                    System.out.println("Erreur d'ecriture d'enregistrement pour inscription");
                }
            }
        }
    }



    /**
     * Sert a valider le numero d'un professionnel
     * @param numero Le numero du professionnel a valider
     * @return true si le numero du professionnel est valide et existe, false sinon
     */
    public static boolean validerNumeroPro(int numero) {
        if (!serverPro.containsKey(numero)){
            System.out.println("Numero invalide.");
            return false;
        }
        return true;
    }

    /**
     * S'assure que le courriel entre est valide
     * @param courriel le courriel a valider
     * @return true si valide, false sinon
     */
    public static boolean validerCourriel(String courriel) {
        return courriel.matches("[a-zA-Z0-9_.-]+@[a-zA-Z]+(\\.[a-zA-Z]+)+");
    }

    public static Date stringToDate(String dateHeure){
        String[] dateSplitHeure = dateHeure.split(" ");

        String[] infosDate = dateSplitHeure[0].split("-");
        int an = Integer.parseInt(infosDate[2]) - 1900;
        int mois = Integer.parseInt(infosDate[1]) - 1;
        int jour = Integer.parseInt(infosDate[0]);

        Date date = new Date(an, mois, jour);
        return date;
    }

    public static Service getService(int numService){
        return serverService.get(numService);
    }

    public static Membre getMembre(int numMembre) {
        return serverMembre.get(numMembre);
    }

    public static Professionnel getPro(int numPro) {
        return serverPro.get(numPro);
    }

    public static Date getDateActuelle() {
        return dateActuelle;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public static TreeMap<Integer, Professionnel> getServerPro() {
        return serverPro;
    }
}
