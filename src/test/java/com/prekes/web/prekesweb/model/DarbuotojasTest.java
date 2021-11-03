package com.prekes.web.prekesweb.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DarbuotojasTest {

	private static int ID = 1;
    private static String VARDAS = "JONAS";
    private static String MIESTAS = "VILNIUS";
    

    @Test
    void testDarbuotojasConstructor() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        assertAll("Test Darbuotojas constructor",
                () -> assertEquals(VARDAS, Darbuotojas.getVardas()),
                () -> assertEquals(MIESTAS, Darbuotojas.getMiestas())
        );
    }

    @Test
    void testHashCode() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        Darbuotojas.setId(ID);
        assertEquals(ID + 31, Darbuotojas.hashCode());
    }

    @Test
    void testCompareTo() {
        Darbuotojas Darbuotojas1 = new Darbuotojas(VARDAS, MIESTAS);
        Darbuotojas Darbuotojas2 = new Darbuotojas(VARDAS, MIESTAS);
        assertEquals(0, Darbuotojas1.compareTo(Darbuotojas2));
    }

    @Test
    void testEqualsObject() {
        Darbuotojas Darbuotojas1 = new Darbuotojas(VARDAS, MIESTAS);
        Darbuotojas Darbuotojas2 = new Darbuotojas(VARDAS, MIESTAS);
        assertTrue(Darbuotojas1.equals(Darbuotojas2));
    }

    @Test
    void testToString() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        assertEquals("Darbuotojas [id=" + Darbuotojas.getId() + ", vardas=" + VARDAS + ", miestas=" + MIESTAS + "]", Darbuotojas.toString());
    }

    @Test
    void testSetId() {
        Darbuotojas Darbuotojas = new Darbuotojas();
        assertThrows(NullPointerException.class, () -> Darbuotojas.setId(-1));
    }

    @Test
    void testSetVardas() {
        Darbuotojas Darbuotojas = new Darbuotojas();
        assertThrows(NullPointerException.class, () -> Darbuotojas.setVardas(""));
    }

    @Test
    void testSetMiestas() {
        Darbuotojas Darbuotojas = new Darbuotojas();
        assertThrows(NullPointerException.class, () -> Darbuotojas.setMiestas(null));
    }

}
