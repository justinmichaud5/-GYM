/*
import java.util.ArrayList;

public class Agent {
    private String id;

    public static boolean checkValide(int noMembre) {
        Membre membre = Systeme.getMembreFromId(noMembre);
        if (membre == null) {
            return false;
        } else if (membre.getValide()) {
            return true;
        }
        return false;
    }

    public static void afficherSeances() {
        Systeme.afficherListeMembres();
    }

    */
/**
     * Fonction qui permet a l'agent d'inscrire un membre a une activite
     *//*

    public static void inscrireMembre(int numMembre, int idSeance) {
        Seance seance = Systeme.getSeanceFromId(idSeance);
        // verif
        for (int i = 0; i < seance.getListeParticipants().size(); i++) {
            if (numMembre == seance.getListeParticipants().get(i).getNumUsager()) {
                System.out.println("Membre deja inscrit a la seance");
                return;
            } else {
                ArrayList listeMembres = Systeme.getListeMembres();
                for (int j = 0; j < listeMembres.size(); j++) {
                    Membre membre = (Membre) listeMembres.get(j);
                    if (numMembre == membre.getNumUsager()) {
                        seance.addParticipant(membre);
                    }
                }
            }
        }
    }

    */
/**
     * Fonction qui permet de confirmer la presence d'un membre a une seance
     *//*

    public static void confirmerInscription(int noMembre, int noSeance) {
        Seance seance = Systeme.getSeanceFromId(noSeance);
        Membre membre = seance.getParticipantFromId(noMembre);

        if (membre.getValide()){
            System.out.println("Valide!");
            seance.creerEnregistrementConf(noMembre);
        } else {
            System.out.println("Membre non valide - acces refuse");
        }
        return;
    }
}*/
