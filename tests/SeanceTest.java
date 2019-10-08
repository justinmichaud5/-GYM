import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour deux methodes de la classe Seance
 */
class SeanceTest {
    Seance seance;
    Membre membreBob;
    Membre membreBib;
    Membre membreBeb;

    @BeforeEach
    void setUp() {
        seance = new Seance("Yoga", "01-01-2020 13:00",
                25.00,100000000, 20, 1234567, 1234567);
        membreBob = new Membre("Bob","allo@ici.com","234 rue allo", "Laval", "Qc",
                "H0H 0H0");
        membreBib = new Membre("Bib","alli@ici.com","234 rue allo", "Laval", "Qc",
                "H0H 0H0");
        membreBeb = new Membre("Beb","alle@ici.com","234 rue allo", "Laval", "Qc",
                "H0H 0H0");
    }

    @Test
    void genererCodeSeance() {
        // Cas type
        assertEquals(seance.genererCodeSeance(123456789,1234567,1234567), 1236789);
        assertNotEquals(seance.genererCodeSeance(123456789,1234567,1234567), 0000000);

        // Valeurs limites
        assertEquals(seance.genererCodeSeance(0,0,0),0000000);
        Assertions.assertThrows(NumberFormatException.class, () -> {
            seance.genererCodeSeance(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
        });

        // Valeurs illégales
        assertEquals(seance.genererCodeSeance(-123456789,-1234567,1234567), -1);
    }

    @Test
    void addParticipant() {

        // Ajouts de Membres dans la liste des participants
        seance.addParticipant(membreBob);
        assertEquals(seance.getListeParticipants().size(), 1);
        seance.addParticipant(membreBib);
        assertEquals(seance.getListeParticipants().size(), 2);
        seance.addParticipant(membreBeb);
        assertEquals(seance.getListeParticipants().size(), 3);

        // Vérification que les membres appropriés ont été ajoutés à la liste de participants
        Membre membreTest1 = seance.getListeParticipants().get(0);
        assertEquals(membreBob, membreTest1);
        assertNotEquals(membreBib, membreTest1);

        // Ajout du même membre
        seance.addParticipant(membreBob);
        assertEquals(seance.getListeParticipants().size(), 3);
    }

    @AfterEach
    void tearDown() {
        seance = null;
        membreBob = null;
        membreBib = null;
        membreBeb = null;
    }
}