import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validerFormatNumService() {
        // Cas accetpant
        assertEquals(Main.validerFormatNumService("1234567"), true);
        assertEquals(Main.validerFormatNumService("0000000"), true);

        // Cas refusant
        assertEquals(Main.validerFormatNumService("123456"), false);
        assertEquals(Main.validerFormatNumService("12345678"), false);
        assertEquals(Main.validerFormatNumService("ABCDEFG"), false);

        //Valeur impossible
        assertEquals(Main.validerFormatNumService("!\"/?&%$%/$\"%/"), false);
        assertEquals(Main.validerFormatNumService("$%?&*()"), false);
    }

    @Test
    void validerCapacite() {
        // Cas acceptant
        assertEquals(Main.validerCapacite("1234567890"), true);
        assertEquals(Main.validerCapacite("3"), true);

        // Cas refusant
        assertEquals(Main.validerCapacite("ABC"), false);
        assertEquals(Main.validerCapacite("±@£±@£¤±"), false);

        // Valeur impossible
        assertEquals(Main.validerCapacite("99999999999999999999999"), false);
    }
}