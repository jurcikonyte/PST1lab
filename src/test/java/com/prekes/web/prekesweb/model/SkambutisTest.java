package com.prekes.web.prekesweb.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SkambutisTest {

	private static int ID = 1;
    private static int ATSILIEPTA = 1;
    private static String LAIKAS = "10.10 10:10";
    private static int DARBUOTOJOID = 1;
    private static int TRUKME = 1;
    

    @Test
    void testSkambutisConstructor() {
        Skambutis Skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        assertAll("Test Skambutis constructor",
                () -> assertEquals(ATSILIEPTA, Skambutis.getAtsiliepta()),
                () -> assertEquals(LAIKAS, Skambutis.getLaikas()),
                () -> assertEquals(DARBUOTOJOID, Skambutis.getDarbuotojoId()),
                () -> assertEquals(TRUKME, Skambutis.getTrukme())
        );
    }

    @Test
    void testHashCode() {
        Skambutis Skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        Skambutis.setId(ID);
        assertEquals(ID + 31, Skambutis.hashCode());
    }

    @Test
    void testCompareTo() {
        Skambutis Skambutis1 = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        Skambutis Skambutis2 = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        assertEquals(0, Skambutis1.compareTo(Skambutis2));
    }

    @Test
    void testEqualsObject() {
        Skambutis Skambutis1 = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        Skambutis Skambutis2 = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        assertTrue(Skambutis1.equals(Skambutis2));
    }

    @Test
    void testToString() {
        Skambutis Skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
        assertEquals("Skambutis [id =" + Skambutis.getId() + ", atsiliepta=" + ATSILIEPTA + ", laikas=" + LAIKAS + ", darbuotojoId=" + DARBUOTOJOID + ", trukme=" + TRUKME + "]", Skambutis.toString());
    }

    @Test
    void testSetId() {
        Skambutis Skambutis = new Skambutis();
        assertThrows(NullPointerException.class, () -> Skambutis.setId(-1));
    }

    @Test
    void testSetDarbuotojoId() {
        Skambutis Skambutis = new Skambutis();
        assertThrows(NullPointerException.class, () -> Skambutis.setDarbuotojoId(-1));
    }

    @Test
    void testSetAtsiliepta() {
        Skambutis Skambutis = new Skambutis();
        assertThrows(NullPointerException.class, () -> Skambutis.setAtsiliepta(-1));
    }

    @Test
    void testSetTrukme() {
        Skambutis Skambutis = new Skambutis();
        assertThrows(NullPointerException.class, () -> Skambutis.setTrukme(-1));
    }

    @Test
    void testSetLaikas() {
        Skambutis Skambutis = new Skambutis();
        assertThrows(NullPointerException.class, () -> Skambutis.setLaikas(null));
    }

}
