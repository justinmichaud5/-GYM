import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour deux methodes de la classe Systeme
 */
class SystemeTest {
    Membre membre1;
    Membre membre2;
    Professionnel pro1;
    Professionnel pro2;

    @BeforeEach
    void setUp() {
        membre1 = new Membre("Erick Raelijohn", "erick.raelijohn@umontreal.ca",
                "12 rue Uni", "Montreal", "Qc", "J1J1J1");
        membre2 = new Membre("Khady Fall", "khady.fall@umontreal.ca",
                "12 rue Uni", "Montreal", "Qc", "J1J1J1");
        pro1 = new Professionnel("Edouard Batot", "batotedo@iro.umontreal.ca",
                "12 rue Uni", "Montreal", "Qc", "J1J1J1");
        pro2 = new Professionnel("Stephen Schach", "schachste@iro.umontreal.ca",
                "12 rue Uni", "Montreal", "Qc", "J1J1J1");
    }



    @Test
    void verifierMembre() {
        // Ce test est prévu pour lorsqu'on a déja vérifié que ajouterCompteMembre fonctionne.
        Systeme.ajouterCompteMembre(membre1);
        Systeme.ajouterCompteMembre(membre2);
        int numMembre1 = membre1.getNumMembre();
        int numMembre2 = membre2.getNumMembre();

        // Cas type (membres existants)
        assertEquals(Systeme.verifierMembre(numMembre1), 1);
        assertEquals(Systeme.verifierMembre(numMembre2), 1);

        // Valeur possible, membre inexistant
        assertEquals(Systeme.verifierMembre(999999999), -1);

        // Valeur impossible, membre inexistant
        assertEquals(Systeme.verifierMembre(-8), -1);
    }

    @Test
    void ajouterComptePro() {
        // Liste de pro vide
        assertEquals(Systeme.getServerPro().size(), 0);

        // Ajout de professionnel dans la liste de professionnels
        Systeme.ajouterComptePro(pro1);
        assertEquals(Systeme.getServerPro().size(), 1);
        Systeme.ajouterComptePro(pro2);
        assertEquals(Systeme.getServerPro().size(), 2);

        // Vérification que les professionnels ajoutés sont les bons
        Professionnel proTest = Systeme.getServerPro().get(100000000);
        assertEquals(pro1, proTest);
        assertNotEquals(new Professionnel("Vaclav Rajlich", "rajlichvac@iro.umontreal.ca",
                "14 rue Software", "Québec", "Qc", "J1J1J1"), proTest);
    }

    @AfterEach
    void tearDownAll(){
        int numMem1 = membre1.getNumMembre();
        int numMem2 = membre2.getNumMembre();
        int numPro1 = pro1.getNumPro();
        int numPro2 = pro2.getNumPro();

        Systeme.supprimerMembre(numMem1);
        Systeme.supprimerMembre(numMem2);
        Systeme.supprimerPro(numPro1);
        Systeme.supprimerPro(numPro2);

        membre1 = null;
        membre2 = null;
        pro1 = null;
        pro2 = null;
    }
}