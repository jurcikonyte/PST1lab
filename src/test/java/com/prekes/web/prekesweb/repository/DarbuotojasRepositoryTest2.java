package com.prekes.web.prekesweb.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.prekes.web.prekesweb.model.Darbuotojas;

@DataJpaTest
class DarbuotojasRepositoryTest2 {


    private static final String VARDAS = "JONAS";
    private static final String MIESTAS1 = "VILNIUS";
    private static final String MIESTAS2 = "KAUNAS";
    @Autowired
    private DarbuotojasRepository darbuotojasRepository;

    @Test
    void testSave() {
        Darbuotojas o = new Darbuotojas(VARDAS, MIESTAS1);
        darbuotojasRepository.save(o);
        List<Darbuotojas> gydytojaiByTelNr = darbuotojasRepository.findByMiestas(MIESTAS1);
        assertNotNull(gydytojaiByTelNr);
        assertEquals(VARDAS, gydytojaiByTelNr.get(0).getVardas());
        System.out.println(gydytojaiByTelNr);
    }

    @Test
    void testFindAll() {
        Darbuotojas o = new Darbuotojas(VARDAS, MIESTAS1);
        darbuotojasRepository.save(o);
        Iterable<Darbuotojas> gydytojai = darbuotojasRepository.findAll();
        assertNotNull(gydytojai);
        List<Darbuotojas> result = new ArrayList<>();
        gydytojai.forEach(result::add);
        assertEquals(1, result.size());
    }

    @Test
    void testDelete() {
        Darbuotojas o = new Darbuotojas(VARDAS, MIESTAS1);
        darbuotojasRepository.save(o);
        Darbuotojas DarbuotojasByVardas = darbuotojasRepository.findOneByVardas(VARDAS);
        assertNotNull(DarbuotojasByVardas);
        darbuotojasRepository.delete(DarbuotojasByVardas);
        Iterable<Darbuotojas> gydytojai = darbuotojasRepository.findAll();
        List<Darbuotojas> result = StreamSupport.stream(gydytojai.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, result.size());
    }

    @Test
    void testfindByTelNr() {
        Darbuotojas Darbuotojas1 = new Darbuotojas(VARDAS, MIESTAS1);
        Darbuotojas Darbuotojas2 = new Darbuotojas(VARDAS, MIESTAS2);
        Darbuotojas Darbuotojas3 = new Darbuotojas(VARDAS, MIESTAS1);
        darbuotojasRepository.save(Darbuotojas1);
        darbuotojasRepository.save(Darbuotojas2);
        darbuotojasRepository.save(Darbuotojas3);
        Iterable<Darbuotojas> gydytojai = darbuotojasRepository.findByMiestas(MIESTAS1);
        assertNotNull(gydytojai);
        List<Darbuotojas> result = StreamSupport.stream(gydytojai.spliterator(), false).collect(Collectors.toList());
        assertEquals(2, result.size());
    }

    @Test
    void testFindByVardasAndTelNr() {
        Darbuotojas Darbuotojas1 = new Darbuotojas(VARDAS, MIESTAS1);
        Darbuotojas Darbuotojas2 = new Darbuotojas(VARDAS, MIESTAS2);
        Darbuotojas Darbuotojas3 = new Darbuotojas(VARDAS, MIESTAS1);
        darbuotojasRepository.save(Darbuotojas1);
        darbuotojasRepository.save(Darbuotojas2);
        darbuotojasRepository.save(Darbuotojas3);
        Iterable<Darbuotojas> gydytojai = darbuotojasRepository.findByVardasAndMiestas(VARDAS, MIESTAS2);
        assertNotNull(gydytojai);
        List<Darbuotojas> result = StreamSupport.stream(gydytojai.spliterator(), false).collect(Collectors.toList());
        assertEquals(1, result.size());
    }
}
