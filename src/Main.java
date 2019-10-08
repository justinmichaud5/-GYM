import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe principale qui represente la partie "Vue" (ou l'interface) du programme.
 */
public class Main {
    private static Scanner input = new Scanner(System.in);
    private static boolean quit = false;
    private final static String[] JOURS = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "" +
                                           "VENDREDI", "SAMEDI", "DIMANCHE"};

    /**
     * Methode principale qui lance le programme de #Gym.
     * @param args arguments en entree de l'application. Non utilise.
     */
    public static void main (String[] args) {
        System.out.println("\n#Gym V1.0");
        menuPrincipal();
    }

    /**
     * Menu principal de l'application. L'operateur peux choisir les commandes avec le
     * clavier afin de naviguer a travers les diverses options.
     */
    private static void menuPrincipal() {
        while(!quit) {
            System.out.println("\n---MENU PRINCIPAL---");
            System.out.println("(0) Valider usager");
            System.out.println("(1) Creer un nouveau compte");
            System.out.println("(2) Options membre");
            System.out.println("(3) Options professionnel");
            System.out.println("(4) Generer rapport hebdomadaire");
            System.out.println("(5) Simulation processus comptable");

            switch(getInput().toUpperCase()) {
                case ("0") :
                    menuValidation();
                    break;
                case ("1") :
                    creerUsager();
                    break;
                case ("2") :
                    menuOptionsMembre();
                    break;
                case ("3") :
                    menuOptionsProfessionnel();
                    break;
                case ("4") :
                    genererRapportSynthese();
                    break;
                case ("5") :
                    Systeme.processusComptable();
                    break;
                case ("Q") :
                    quit = true;
                    break;
                default :
                    System.out.println("Commande invalide, reessayez");
                    break;
            }
        }
    }

    /**
     * Selection du type de validation souhaite (membre ou professionnel)
     */
    private static void menuValidation() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\n(1) Valider membre/professionnel par courriel");
            System.out.println("(2) Valider membre par numero de membre");
            System.out.println("(3) Valider professionnel par numero de professionnel");
            System.out.println("(Q) Retour");
            switch (getInput().toUpperCase()) {
                case "1":
                    validerCourriel();
                    inputValide = true;
                    break;
                case "2":
                    validerNumeroMembre();
                    inputValide = true;
                    break;
                case "3":
                    validerNumeroPro();
                    inputValide = true;
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("Commande invalide, reessayez");
            }
        }
    }

    /**
     * Verifie si le courriel de l'usager existe et est valide
     */
    private static void validerCourriel() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nCourriel de l'usager :");
            String courriel = getInput();
            if (courriel.equalsIgnoreCase("Q")) {
                return;
            }
            if (Systeme.validerCourriel(courriel)) {
                inputValide = true;
                Systeme.verifierCourriel(courriel);
            } else {
                System.out.println("\nCourriel invalide, veuillez reessayer");
            }
        }
    }

    /**
     * Verifie si le numero du membre existe et est valide
     */
    private static void validerNumeroMembre() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nNumero du membre :");
            try {
                String numMembreStr = getInput();
                if (numMembreStr.equalsIgnoreCase("Q")) {
                    return;
                }
                int numMembre = Integer.parseInt(numMembreStr);
                inputValide = true;
                Systeme.verifierMembre(numMembre);
            } catch (Exception e) {
                System.out.println("\nNumero invalide, veuillez reessayer");
            }
        }
    }

    /**
     * Verifie si le numero du professionnel existe et est valide
     */
    private static void validerNumeroPro() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nNumero du professionnel :");
            try {
                String numProStr = getInput();
                if (numProStr.equalsIgnoreCase("Q")) {
                    return;
                }
                int numPro = Integer.parseInt(numProStr);
                inputValide = true;
                Systeme.verifierPro(numPro);
            } catch (Exception e) {
                System.out.println("\nNumero invalide, veuillez reessayer");
            }
        }
    }

    /**
     * Gere la creation d'un compte membre ou professionnel
     */
    private static void creerUsager() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\n(1) Nouveau membre");
            System.out.println("(2) Nouveau professionnel");
            System.out.println("(Q) Retour");
            switch (getInput().toUpperCase()) {
                case "1":
                    formulaireUsager(true);
                    inputValide = true;
                    break;
                case "2":
                    formulaireUsager(false);
                    inputValide = true;
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("Commande invalide, veuillez reessayer");
            }
        }
    }

    /**
     * Gere la creation d'un compte d'un membre
     */
    private static void formulaireUsager(boolean newMembre) {
        boolean inscriptionConfirme = false;

        // Info
        String nom = "";
        String courriel = "";
        String adresse = "";
        String ville = "";
        String province = "";
        String codePostal = "";

        Usager compte;
        while(!inscriptionConfirme) {
            boolean inputValide = false;
            // Nom
            while (!inputValide) {
                System.out.println("\nNom complet : \"Prenom Nom\"");
                nom = getInput();
                if (nom.length() > 25) {
                    System.out.println("\nNom trop long, veuillez reessayer");
                } else {
                    inputValide = true;
                }
            }

            // Courriel
            inputValide = false;
            while (!inputValide) {
                System.out.println("\nCourriel : ");
                courriel = getInput();

                if (Systeme.validerCourriel(courriel)) {
                    inputValide = true;
                } else {
                    System.out.println("\nFormat de courriel invalide, veuillez reessayer");
                }
            }

            // Adresse
            inputValide = false;
            while (!inputValide) {
                System.out.println("\nAdresse (max. 25 caracteres) : ");
                adresse = getInput();

                if (adresse.length() > 25) {
                    System.out.println("\nAdresse trop longue, veuillez reessayer");
                } else {
                    inputValide = true;
                }
            }

            // Ville
            inputValide = false;
            while (!inputValide) {
                System.out.println("\nVille (max. 14 caracteres) : ");
                ville = getInput();

                if (ville.length() > 14) {
                    System.out.println("\nNom de la ville trop long, veuillez reessayer");
                } else {
                    inputValide = true;
                }
            }

            // Province
            inputValide = false;
            while (!inputValide) {
                System.out.println("\nProvince (ex.: Qc, On, etc.) (max. 2 caracteres) : ");
                province = getInput();

                if (province.length() > 2) {
                    System.out.println("\nCode de province trop long, veuillez reessayer");
                } else {
                    inputValide = true;
                }
            }

            // Code Postal
            inputValide = false;
            while (!inputValide) {
                System.out.println("\nCode Postal (max. 6 caracteres)  : ");
                codePostal = getInput();

                if (codePostal.length() != 6) {
                    System.out.println("\nCode postal trop long, veuillez reessayer");
                } else {
                    inputValide = true;
                }
            }

            // Confirmation
            inputValide = false;
            while(!inputValide) {
                System.out.println("\nNom : " + nom);
                System.out.println("Courriel : " + courriel);
                System.out.println("Adresse : " + adresse);
                System.out.println("Ville : " + ville);
                System.out.println("Province : " + province);
                System.out.println("Code Postal : " + codePostal);
                System.out.println("Informations valides? (O/N/C)");

                String confirmation = getInput();
                switch (confirmation.toUpperCase()) {
                    case "O" :
                        if (newMembre) {
                            if (demanderPaiement()) {
                                inputValide = true;
                                inscriptionConfirme = true;

                                compte = new Membre(nom, courriel, adresse, ville, province, codePostal);
                                Systeme.ajouterCompteMembre((Membre) compte);

                                System.out.println("\nNumero de membre : " + ((Membre) compte).getNumMembre());
                                System.out.println("Creation de compte reussie!");
                                System.out.println("Appuyez sur \"Enter\"");
                                getInput();
                            } else {
                                System.out.println("\nAnnulation de la creation de compte");
                                return;
                            }
                        } else {
                            inputValide = true;
                            inscriptionConfirme = true;
                            compte = new Professionnel(nom, courriel, adresse, ville, province, codePostal);
                            Systeme.ajouterComptePro((Professionnel) compte);
                            System.out.println("\nNumero de professionnel : " + ((Professionnel) compte).getNumPro());
                            System.out.println("Creation de compte reussie!");
                            System.out.println("Appuyez sur \"Enter\"");
                            input.nextLine();
                        }
                        break;
                    case "C" :
                        System.out.println("\nAnnulation de la creation de compte");
                        return;
                    case "N" :
                        inputValide = true;
                        break;
                    default :
                        System.out.println("\nCommande invalide, veuillez reessayer");
                }
            }
        }
    }

    /**
     * Permet d'attendre la fin du paiement avant de continuer les operations
     *
     * @return true si on confirme le paiement, false sinon
     */
    public static boolean demanderPaiement() {
        while(true) {
            System.out.println("\nProcedez au payement");
            System.out.println("Payement accepte? (O/N)");
            switch (getInput().toUpperCase()) {
                case "O":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Commande invalide, reessayez");
            }
        }
    }

    /**
     * Gere le sous-menu des options pour les membres
     */
    private static void menuOptionsMembre() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\n(1) Consulter les seances disponibles");
            System.out.println("(2) Inscription a une seance");
            System.out.println("(3) Confirmation d'une inscription a une seance");
            System.out.println("(4) Modifier compte membre");
            System.out.println("(5) Supprimer compte membre");
            System.out.println("(Q) Retour");
            switch (getInput().toUpperCase()) {
                case "1":
                    consulterSeancesDispos();
                    inputValide = true;
                    break;
                case "2":
                    inscriptionSeance();
                    inputValide = true;
                    break;
                case "3":
                    confirmerInscription();
                    inputValide = true;
                    break;
                case "4":
                    modifierCompteMembre();
                    inputValide = true;
                    break;
                case "5":
                    supprimerCompteMembre();
                    inputValide = true;
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("Commande invalide, veuillez reessayer");
            }
        }
    }

    /**
     * Permet de consulter les seances disponibles pour inscription en les affichant
     */
    private static void consulterSeancesDispos() {
        System.out.println("Voici la liste de seances disponibles : \n");
        Systeme.afficherListeSeances();
    }

    /**
     * Permet l'inscription d'un membre a une seance
     * HYPOTHeSE : le montant a payer est dû dans le compte du membre des l'inscription pour eviter les
     * annulations et les gens qui ne se presentent pas. Le membre en est averti par l'agent.
     */
    private static void inscriptionSeance() {
        consulterSeancesDispos();
        boolean inputValide = false;
        Service service = null;
        Seance seance = null;
        Membre membre = null;
        DecimalFormat df2 = new DecimalFormat("0.00");

        //Saisie et recherche du service
        System.out.println("Entrer le code du service choisi (7 chiffres) : ");
        while(!inputValide) {
            String numService = getInput();
            if (numService.equalsIgnoreCase("Q")){
                return;
            }
            try {
                service = Systeme.getService(Integer.parseInt(numService));
            } catch (Exception e){
            }
            if (service != null){
                inputValide = true;
            } else {
                System.out.println("Code de service invalide, veuillez reessayer");
            }
        }

        //Saisie et recherche de la seance
        inputValide = false;
        System.out.println("Entrer le code de la seance choisie (7 chiffres) : ");
        while(!inputValide) {
            String numSeance = getInput();
            if (numSeance.equalsIgnoreCase("Q")){
                return;
            }
            try{
                seance = service.getSeance(Integer.parseInt(numSeance));
            } catch (Exception e){
            }
            if (seance != null){
                inputValide = true;
            } else{
                System.out.println("Code de seance invalide, veuillez reessayer");
            }


            /*if (seance != null){
                try {
                    Systeme.inscrireMembre(seance, membre);
                } catch (Exception e) {
                    System.out.println("Bug a l'enregistrement de l'inscription du membre. Contactez les developpeurs");
                }
                inputValide = true;
            } else {
                System.out.println("Code de seance invalide, veuillez reessayer");
            }*/
        }

        //Saisie et recherche du membre
        inputValide = false;
        System.out.println("Entrer le numero du membre a inscrire (9 chiffres) :");
        while(!inputValide) {
                        String numMembre = getInput();
            if (numMembre.equalsIgnoreCase("Q")){
                return;
            }
            try{
                switch (Systeme.verifierMembre(Integer.parseInt(numMembre))){
                    case 1:
                        membre = Systeme.getMembre(Integer.parseInt(numMembre));
                        break;
                    case 0:
                        System.out.println("Veuillez d'abord gerer la validite du compte dans les options membre.");
                        return;
                    case -1:
                        System.out.println("Numero de membre invalide, veuillez reessayer");
                        break;
                }
            } catch (Exception e){
                System.out.println("Numero de membre invalide, veuillez reessayer");
            }
            if (membre != null){
                inputValide = true;
            }
        }

        if (membre.getIncriptionsSeances().contains(seance)) {
            System.out.println("Membre deja inscrit a la seance. Cancelation de l'inscription.");
            return;
        }

        System.out.println(seance);
        System.out.println("Prix de l'inscription a la seance : " + df2.format(seance.getFrais()));
        System.out.println("Confirmer l'inscription? (O/N)");

        //Inscription du membre
        inputValide = false;
        while(!inputValide) {
            String commande = getInput();
            if(commande.equalsIgnoreCase("O")){
                try {
                    Systeme.inscrireMembre(seance, membre);
                    inputValide = true;
                    System.out.println("\nInscription reussie!");
                    System.out.println("Coût de l'inscription ajoute au compte du membre.");
                } catch (Exception e) {
                    System.out.println("Bug a l'enregistrement de l'inscription du membre. Contactez les developpeurs");
                }

            } else if (commande.equalsIgnoreCase("N")){
                System.out.println("\nInscription annulee.");
                return;
            }
        }

    }

    /**
     * Permet de confirmer l'inscription d'un membre a une seance
     */
    private static void confirmerInscription() {
        Membre membre = null;
        boolean inputValide = false;
        Service service = null;
        Seance seance = null;

        //Recherche et validite du membre
        while (!inputValide) {
            System.out.println("\nNumero du membre inscrit :");
            String numMembre = getInput();
            if (numMembre.equalsIgnoreCase("Q")) {
                return;
            }
            membre = Systeme.getMembre(Integer.parseInt(numMembre));

            switch (Systeme.verifierMembre(Integer.parseInt(numMembre))){
                case 1:
                    membre = Systeme.getMembre(Integer.parseInt(numMembre));
                    inputValide = true;
                    System.out.println("Nom du membre : " + membre.getNom());
                    break;
                case 0:
                    System.out.println("Veuillez d'abord gerer la validite du compte dans les options membre.");
                case -1:
                    break;
            }
        }

        //Affichage des seances liees au membre
        System.out.println("\nVoici les seances ou ce membre est inscrit :");
        membre.afficherSeances();

        //Saisie du code du service pour faciliter la recherche et valider l'exactitude du choix
        inputValide = false;
        while(!inputValide) {
            System.out.println("Entrer le code du service a confirmer :");
            String numService = getInput();
            if (numService.equalsIgnoreCase("Q")){
                return;
            }
            service = Systeme.getService(Integer.parseInt(numService));
            if (service != null){
                inputValide = true;
            } else {
                System.out.println("Code de service invalide, veuillez reessayer");
            }
        }

        //Saisie et recherche de la seance
        inputValide = false;
        while(!inputValide) {
            System.out.println("Entrer le code de la seance a confirmer :");
            String numSeance = getInput();
            if (numSeance.equalsIgnoreCase("Q")){
                return;
            }
            seance = service.getSeance(Integer.parseInt(numSeance));
            if (seance != null){
                inputValide = true;
            } else {
                System.out.println("Code de seance invalide, veuillez reessayer");
            }
        }

        try {
            if (Systeme.confirmerInscription(seance, membre)) {
                System.out.println("\nValide");
            } else {
                System.out.println("\nErreur : le membre n'est pas inscrit ou l'inscription est invalide.");
            }
        } catch (Exception e){
            System.out.println("Bug a la confirmation du membre. Contactez les developpeurs");
        }

    }

    /**
     * Modifie les informations d'un membre
     */
    private static void modifierCompteMembre() {
        Membre membre = null;
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nNumero du membre a modifier :");
            String numMembre = getInput();
            if (numMembre.toUpperCase().equals("Q")) {
                return;
            }
            membre = Systeme.getMembre(Integer.parseInt(numMembre));

            switch (Systeme.verifierMembre(Integer.parseInt(numMembre))){
                case 1:
                case 0:
                    membre = Systeme.getMembre(Integer.parseInt(numMembre));
                    inputValide = true;
                    break;
                case -1:
                    break;
            }
        }

        inputValide = false;
        while (!inputValide) {
            System.out.println("\nModifier :");
            System.out.println("(1) Nom : " + membre.getNom());
            System.out.println("(2) Courriel : " + membre.getCourriel());
            System.out.println("(3) Adresse : " + membre.getAdresse());
            System.out.println("(4) Ville : " + membre.getVille());
            System.out.println("(5) Province : " + membre.getProvince());
            System.out.println("(6) Code Postal : " + membre.getCodePostal());
            System.out.println("(Q) Canceler la modification");

            switch (getInput().toUpperCase()) {
                case ("1") : // Nom
                    while (!inputValide) {
                        System.out.println("\nNom courant : " + membre.getNom());
                        System.out.println("Nouveau nom complet : \"Prenom Nom\"");
                        String nom = getInput();
                        if (nom.length() > 25) {
                            System.out.println("\nNom trop long, veuillez reessayer");
                        } else {
                            inputValide = true;
                            membre.setNom(nom);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("2") : // Courriel
                    while (!inputValide) {
                        System.out.println("\nCourriel courrant : " + membre.getCourriel());
                        System.out.println("Nouveau courriel :");
                        String courriel = getInput();

                        if (Systeme.validerCourriel(courriel)) {
                            inputValide = true;
                            membre.setCourriel(courriel);
                            System.out.println("\nModification reussie!");
                        } else {
                            System.out.println("\nFormat de courriel invalide, veuillez reessayer");
                        }
                    }
                    break;
                case ("3") : // Adresse
                    while (!inputValide) {
                        System.out.println("\nAdresse courante : " + membre.getAdresse());
                        System.out.println("Nouvelle adresse:");
                        String adresse = getInput();

                        if (adresse.length() > 25) {
                            System.out.println("\nAdresse trop longue (Max. 25 caracteres), veuillez reessayer");
                        } else {
                            inputValide = true;
                            membre.setAdresse(adresse);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("4") : // Ville
                    while (!inputValide) {
                        System.out.println("\nVille courante : " + membre.getVille());
                        System.out.println("Nouvelle ville :");
                        String ville = getInput();

                        if (ville.length() > 25) {
                            System.out.println("\nNom de la ville trop long, veuillez en choisir un plus court (max. 25 caracteres)");
                        } else {
                            inputValide = true;
                            membre.setVille(ville);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("5") : // Province
                    while (!inputValide) {
                        System.out.println("\nProvince courante : " + membre.getProvince());
                        System.out.println("Nouvelle province :");
                        String province = getInput();

                        if (province.length() > 2) {
                            System.out.println("\nNom de la province trop long, veuillez en choisir un plus court (max. 25 caracteres)");
                        } else {
                            inputValide = true;
                            membre.setProvince(province);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("6") : // Code Postal
                    inputValide = false;
                    while (!inputValide) {
                        System.out.println("\nCode postal courrant : " + membre.getCodePostal());
                        System.out.println("Nouveau code postal :");
                        String codePostal = getInput();

                        if (codePostal.length() != 6) {
                            System.out.println("\nLongueur invalide, veuillez reessayer");
                        } else {
                            inputValide = true;
                            membre.setCodePostal(codePostal);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("Q") :
                    inputValide=true;
                    System.out.println("\nModification annulee");
                    break;
                default :
                    System.out.println("Commande invalide, reessayez");
            }
        }
    }

    /**
     * Supprime le compte d'un membre
     */
    private static void supprimerCompteMembre() {
        Membre membre = null;
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nNumero du membre a modifier :");
            String numMembre = getInput();
            if (numMembre.toUpperCase().equals("Q")) {
                return;
            }
            membre = Systeme.getMembre(Integer.parseInt(numMembre));
            switch (Systeme.verifierMembre(Integer.parseInt(numMembre))){
                case 1:
                case 0:
                    membre = Systeme.getMembre(Integer.parseInt(numMembre));
                    inputValide = true;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Nom : " + membre.getNom());
        System.out.println("Courriel : " + membre.getCourriel());
        System.out.println("Adresse : " + membre.getAdresse());
        System.out.println("Ville : " + membre.getVille());
        System.out.println("Province : " + membre.getProvince());
        System.out.println("Code Postal : " + membre.getCodePostal());

        System.out.println("\nSupprimer ce membre? (O/N)");
        String confirmation = getInput();
        switch (confirmation.toUpperCase()) {
            case "O" :
                Systeme.supprimerMembre(membre.getNumMembre());
                System.out.println("Suppression reussie!");
                break;
            case "N" :
                System.out.println("Suppression annulee");
                break;
            default :
                System.out.println("\nCommande invalide, veuillez reessayer");
        }
    }

    /**
     * Gere le sous-menu des options pour les professionnels
     */
    private static void menuOptionsProfessionnel() {
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\n(1) Consulter les inscriptions a une seance");
            System.out.println("(2) Creer un service");
            System.out.println("(3) Modifier un service");
            System.out.println("(4) Supprimer un service");
            System.out.println("(5) Modifier un compte professionnel");
            System.out.println("(6) Supprimer un compte professionnel");
            System.out.println("(Q) Retour");
            switch (getInput().toUpperCase()) {
                case "1":
                    consulterInscriptionSeance();
                    inputValide = true;
                    break;
                case "2":
                    creerService();
                    inputValide = true;
                    break;
                case "3":
                    modifierService();
                    inputValide = true;
                    break;
                case "4":
                    supprimerService();
                    inputValide = true;
                    break;
                case "5":
                    modifierComptePro();
                    inputValide = true;
                    break;
                case "6":
                    supprimerComptePro();
                    inputValide = true;
                    break;
                case "Q":
                    return;
                default:
                    System.out.println("Commande invalide, veuillez reessayer");
            }
        }
    }

    /**
     * Permet de consulter les inscriptions a une seance specifique
     */
    protected static void consulterInscriptionSeance() {
        boolean inputValide = false;
        Service service = null;
        Seance seance = null;
        while (!inputValide) {
            System.out.println("Entrez le numero du professionnel :");
            String numPro = getInput();

            if (numPro.equalsIgnoreCase("q")){
                return;
            }

            if(Systeme.verifierPro(Integer.parseInt(numPro))){
                System.out.println("Liste des seances liees a ce professionnel :");
                Systeme.consulterSeances(Integer.parseInt(numPro));
                inputValide = true;
            } else {
                System.out.println("Veuillez reessayer");
            }
        }

        //Saisie du code du service pour faciliter la recherche et valider l'exactitude du choix
        inputValide = false;
        while(!inputValide) {
            System.out.println("Entrer le code du service choisi :");
            String numService = getInput();
            if (numService.equalsIgnoreCase("Q")){
                return;
            }
            service = Systeme.getService(Integer.parseInt(numService));
            if (service != null){
                inputValide = true;
            } else {
                System.out.println("Code de service invalide, veuillez reessayer");
            }
        }

        //Saisie et recherche de la seance
        inputValide = false;
        while(!inputValide) {
            System.out.println("Entrer le code de la seance choisie :");
            String numSeance = getInput();
            if (numSeance.equalsIgnoreCase("Q")){
                return;
            }
            seance = service.getSeance(Integer.parseInt(numSeance));
            if (seance != null){
                inputValide = true;
            } else {
                System.out.println("Code de seance invalide, veuillez reessayer");
            }
        }

        Systeme.consulterInscriptions(seance);

    }

    /**
     * Cree un service offert par un professionnel
     */
    private static void creerService() {
        try {
            System.out.println("Nom du service :");
            String nomService = getInput();

            String dateHeureActuelles = Systeme.getDateActuelle().toString();

            int idProfessionnel;
            do {
                System.out.println("Numero professionnel responsable du service :");
                String num = getInput();
                idProfessionnel = Integer.parseInt(num);
            } while (!Systeme.validerNumeroPro(idProfessionnel));

            String dateDebut;
            do {
                System.out.println("Date de debut du service (format JJ-MM-AAAA) :");
                dateDebut = getInput();
            } while (!validerFormatDate(dateDebut, Systeme.getSimpleDateFormat().format(Systeme.getDateActuelle())));

            String dateFin;
            do {
                System.out.println("Date de fin du service (format JJ-MM-AAAA):");
                dateFin = getInput();
            }
            while (!validerFormatDate(dateFin, dateDebut));

            String heureMinutes;
            do {
                System.out.println("Heure du service (format HH:MM) :");
                heureMinutes = getInput();
            } while (!validerFormatHeure(heureMinutes));

            String fraisTexte;
            do {
                System.out.println("Frais du service (format XXX.XX) :");
                fraisTexte = getInput();
            } while (!validerMonetaire(fraisTexte));
            double frais = Double.parseDouble(fraisTexte);

            String joursSemaineStr;
            ArrayList<String> joursSemaineLst;
            do {
                System.out.println("Jours de la semaine lors desquels ce service est offert : (Lundi Mardi Mercredi ...)");
                joursSemaineStr = getInput() + " ";
                joursSemaineLst = joursSemaineParser(joursSemaineStr);
            } while (!validerJoursSemaine(joursSemaineLst));

            String capaciteTexte;
            do {
                System.out.println("Capacite maximale d'inscription :");
                capaciteTexte = getInput();
            } while (!validerCapacite(capaciteTexte));
            int capacite = Integer.parseInt(capaciteTexte);

            System.out.println("Commentaires (Facultatif) :");
            String commentaires = getInput();

            //Cree le nouveau service et l'ajoute a la liste
            Service nouveauService = new Service(
                    nomService, dateHeureActuelles, dateDebut, dateFin, heureMinutes, joursSemaineLst,
                    idProfessionnel, capacite, frais, commentaires);
            Systeme.ajouterService(nouveauService, idProfessionnel);

            System.out.println("Service ajoute. Code du service : " + nouveauService.getCodeService() + "\n");

        } catch (ParseException e) {
            System.out.println("Parametres de seance invalides. Veuillez reessayer.");
            creerService();
            return;
        } catch (NumberFormatException e) {
            System.out.println("Parametres de seance invalides. Veuillez reessayer.");
            creerService();
            return;
        }
    }

    /**
     * Modifie un service deja cree
     */
    private static void modifierService() {
        String numServiceStr;
        do {
            System.out.println("Entrez le numero du service a modifier (7 chiffres):");
            numServiceStr = getInput();
            if (numServiceStr.equalsIgnoreCase("Q")) {
                return;
            }
        } while (!validerFormatNumService(numServiceStr));
        int numService = Integer.parseInt(numServiceStr);
        Systeme.supprimerService(numService);
        System.out.println("Entrez les nouvelles informations pour le service a modifier");
        creerService();
    }

    /**
     * Supprime un service existant
     */
    private static void supprimerService() {
        String numServiceStr;
        do {
            System.out.println("Entrez le numero du service a supprimer (7 chiffres):");
            numServiceStr = getInput();
            if (numServiceStr.equalsIgnoreCase("Q")) {
                return;
            }
        } while (!validerFormatNumService(numServiceStr));
        int numService = Integer.parseInt(numServiceStr);
        Systeme.supprimerService(numService);
        System.out.println("Service supprime!");
    }

    /**
     * Modifie le compte existant d'un professionnel
     */
    private static void modifierComptePro() {
        Professionnel pro = null;
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nNumero du professionnel a modifier :");
            String commande = getInput();
            if (commande.toUpperCase().equals("Q")) {
                return;
            }
            pro = Systeme.getPro(Integer.parseInt(commande));
            if (pro != null) {
                inputValide = true;
            } else {
                System.out.println("\nNumero de pro invalide, reessayez");
            }
        }

        inputValide = false;
        while (!inputValide) {
            System.out.println("\nModifier :");
            System.out.println("(1) Nom : " + pro.getNom());
            System.out.println("(2) Courriel : " + pro.getCourriel());
            System.out.println("(3) Adresse : " + pro.getAdresse());
            System.out.println("(4) Ville : " + pro.getVille());
            System.out.println("(5) Province : " + pro.getProvince());
            System.out.println("(6) Code Postal : " + pro.getCodePostal());
            System.out.println("(Q) Canceler la modification");

            switch (getInput().toUpperCase()) {
                case ("1"): // Nom
                    while (!inputValide) {
                        System.out.println("\nNom courant : " + pro.getNom());
                        System.out.println("Nouveau nom complet : \"Prenom Nom\"");
                        String nom = getInput();
                        if (nom.length() > 25) {
                            System.out.println("\nNom trop long, veuillez reessayer");
                        } else {
                            inputValide = true;
                            pro.setNom(nom);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("2"): // Courriel
                    while (!inputValide) {
                        System.out.println("\nCourriel courrant : " + pro.getCourriel());
                        System.out.println("Nouveau courriel :");
                        String courriel = getInput();

                        if (Systeme.validerCourriel(courriel)) {
                            inputValide = true;
                            pro.setCourriel(courriel);
                            System.out.println("\nModification reussie!");
                        } else {
                            System.out.println("\nFormat de courriel invalide, veuillez reessayer");
                        }
                    }
                    break;
                case ("3"): // Adresse
                    while (!inputValide) {
                        System.out.println("\nAdresse courante : " + pro.getAdresse());
                        System.out.println("Nouvelle adresse :");
                        String adresse = getInput();

                        if (adresse.length() > 25) {
                            System.out.println("\nAdresse trop longue (Max. 25 caracteres), veuillez reessayer");
                        } else {
                            inputValide = true;
                            pro.setAdresse(adresse);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("4"): // Ville
                    while (!inputValide) {
                        System.out.println("\nVille courante : " + pro.getVille());
                        System.out.println("Nouvelle ville :");
                        String ville = getInput();

                        if (ville.length() > 25) {
                            System.out.println("\nNom de la ville trop long, veuillez en choisir un plus court (max. 25 caracteres)");
                        } else {
                            inputValide = true;
                            pro.setVille(ville);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("5"): // Province
                    while (!inputValide) {
                        System.out.println("\nProvince courante : " + pro.getProvince());
                        System.out.println("Nouvelle province :");
                        String province = getInput();

                        if (province.length() > 2) {
                            System.out.println("\nNom de la province trop long, veuillez en choisir un plus court (max. 25 caracteres)");
                        } else {
                            inputValide = true;
                            pro.setProvince(province);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("6"): // Code Postal
                    inputValide = false;
                    while (!inputValide) {
                        System.out.println("\nCode postal courrant : " + pro.getCodePostal());
                        System.out.println("Nouveau code postal :");
                        String codePostal = getInput();

                        if (codePostal.length() != 6) {
                            System.out.println("\nLongueur invalide, veuillez reessayer");
                        } else {
                            inputValide = true;
                            pro.setCodePostal(codePostal);
                            System.out.println("\nModification reussie!");
                        }
                    }
                    break;
                case ("Q"):
                    inputValide = true;
                    System.out.println("\nModification annulee");
                    break;
                default:
                    System.out.println("Commande invalide, reessayez");
            }
        }
    }

    /**
     * Supprime le compte d'un professionnel
     */
    private static void supprimerComptePro() {
        Professionnel pro = null;
        boolean inputValide = false;
        while (!inputValide) {
            System.out.println("\nNumero du professionnel a modifier :");
            String commande = getInput();
            if (commande.toUpperCase().equals("Q")) {
                return;
            }
            pro = Systeme.getPro(Integer.parseInt(commande));
            if (pro != null) {
                inputValide = true;
            } else {
                System.out.println("\nNumero de professionnel invalide, reessayez");
            }
        }
        System.out.println("Nom : " + pro.getNom());
        System.out.println("Courriel : " + pro.getCourriel());
        System.out.println("Adresse : " + pro.getAdresse());
        System.out.println("Ville : " + pro.getVille());
        System.out.println("Province : " + pro.getProvince());
        System.out.println("Code Postal : " + pro.getCodePostal());

        System.out.println("\nSupprimer ce professionnel? (O/N)");
        String confirmation = getInput();
        switch (confirmation.toUpperCase()) {
            case "O" :
                Systeme.supprimerPro(pro.getNumPro());
                System.out.println("Suppression reussie!");
                break;
            case "N" :
                System.out.println("Suppression annulee");
                break;
            default :
                System.out.println("\nCommande invalide, veuillez reessayer");
        }
    }

    /**
     * Genere le rapport hebdomadaire sur demande
     */
    private static void genererRapportSynthese() {
        Systeme.genererRapportComptable();
    }

    /**
     * Utilitaire pour separer un String de jours de semaine en un tableau
     * @param jours Le String de jours de semaine
     * @return Un tableau de jours de semaine
     */
    public static ArrayList<String> joursSemaineParser(String jours){
        ArrayList<String> joursRecurrence = new ArrayList<String>(7);
        for (int i = 0; i < jours.length(); i++) {
            String mot = "";
            while (jours.charAt(i) != ' ') {
                mot += jours.charAt(i);
                i++;
            }
            joursRecurrence.add(mot);
        }
        return joursRecurrence;
    }

    /**
     * Utilitaire pour valider le format et la validite d'une date
     * @param dateStr Date entree par l'usager
     * @param dateMinStr Date a depasser
     * @return True si la date est valide et du bon format
     */
    public static boolean validerFormatDate(String dateStr, String dateMinStr){
        SimpleDateFormat sdf = Systeme.getSimpleDateFormat();
        Date date = null;
        try {
            date = sdf.parse(dateStr);
            Date dateMin = sdf.parse(dateMinStr);
            if (date.before(dateMin)) {
                System.out.println("La date doit etre posterieure a cette date: " + sdf.format(dateMin));
                return false;
            }
            Date dateLimite = null;
            dateLimite = Systeme.getSimpleDateFormat().parse(("31-12-2100"));
            if (date.after(dateLimite)){
                System.out.println("Date trop eloignee dans le futur. Veuillez selectionner une date plus rapprochee.");
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Format de date invalide");
            return false;
        }
        return true;
    }

    /**
     * Utilitaire pour valider le format et la validite d'une heure (hh:mm)
     * @param heureStr Heure entree par l'usager
     * @return True si l'heure est valide et du bon format
     */
    public static boolean validerFormatHeure(String heureStr){
        if (heureStr.matches("(^[01]?[0-9]|2[0-3]):[0-5][0-9]$")){
            return true;
        }
        return false;
    }

    /**
     * Utilitaire pour valider le format d'un montant d'argent (xxx.xx)
     * @param montant Montant d'argent entre par l'usager
     * @return True si le montant est du bon format
     */
    public static boolean validerMonetaire(String montant){
        if (montant.matches("^[0-9]+(\\.[0-9]{2})?$")){
            return true;
        }
        return false;
    }

    /**
     * Utilitaire pour valider le format de capacite (chiffres seulement)
     * @param capacite Capacite du cours
     * @return True si le format est valide
     */
    public static boolean validerCapacite(String capacite){
        try {
            Integer.parseInt(capacite);
        } catch (Exception e){
            System.out.println("Format invalde");
            return false;
        }
        return true;
    }

    /**
     * Utilitaire pour verifier si les jours entres par l'usager son valides
     * @param joursSemaineStr Liste des jours entres par l'usager
     * @return True si tous les jours entres sont valide
     */
    public static boolean validerJoursSemaine(ArrayList<String> joursSemaineStr){
        for (int i = 0; i < joursSemaineStr.size(); i++) {
            String jour = joursSemaineStr.get(i);
            boolean jourValide = false;
            for (int j = 0; j < JOURS.length; j++) {
                if (jour.toUpperCase().equals(JOURS[j])) {
                    jourValide = true;
                    break;
                }
            }
            if (!jourValide){
                return false;
            }
        }
        return true;
    }

    /**
     * Utilitaire pour verifier si le numero de service entre est du bon format
     * @param numService Le numero de service a tester
     * @return true si le numero est du bon format (7 chiffres)
     */
    public static boolean validerFormatNumService(String numService){
        return numService.matches("^[0-9]{7}$");
    }

    /**
     * Recupere la commande entree par l'usager en ligne de commande
     * @return l'entree de commande
     */
    public static String getInput() {
        System.out.print("> ");
        return input.nextLine();
    }
}