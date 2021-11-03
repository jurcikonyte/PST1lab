package com.prekes.web.prekesweb.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.model.Skambutis;

@DataJpaTest
class SkambutisRepositoryTest {

    private static final int ATSILIEPTA = 1;
    private static final int DARBUOTOJOID1 = 1;
    private static final int DARBUOTOJOID2 = 2;
    private static final String LAIKAS1 = "LAIKAS1";
    private static final String LAIKAS2 = "LAIKAS2";
    private static final int TRUKME1 = 1;
    private static final int TRUKME2 = 2;
    @Autowired
    private SkambutisRepository skambutisRepository;

    @Test
    void testSave() {
        Skambutis o = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME1);
        skambutisRepository.save(o);
        List<Skambutis> darbuotojaiByMiestas = skambutisRepository.findByDarbuotojoId(DARBUOTOJOID1);
        assertNotNull(darbuotojaiByMiestas);
        assertEquals(ATSILIEPTA, darbuotojaiByMiestas.get(0).getAtsiliepta());
        System.out.println(darbuotojaiByMiestas);
    }

    @Test
    void testFindAll() {
        Skambutis o = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME1);
        skambutisRepository.save(o);
        Iterable<Skambutis> darbuotojai = skambutisRepository.findAll();
        assertNotNull(darbuotojai);
        List<Skambutis> result = new ArrayList<>();
        darbuotojai.forEach(result::add);
        assertEquals(1, result.size());
    }

    @Test
    void testDelete() {
        Skambutis o = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME1);
        skambutisRepository.save(o);
        Skambutis skambutisByskambutis = skambutisRepository.findOneBySkambutis(LAIKAS1);
        assertNotNull(skambutisByskambutis);
        skambutisRepository.delete(skambutisByskambutis);
        Iterable<Skambutis> darbuotojai = skambutisRepository.findAll();
        List<Skambutis> result = StreamSupport.stream(darbuotojai.spliterator(), false).collect(Collectors.toList());
        assertEquals(0, result.size());
    }

    @Test
    void testfindByTelNr() {
        Skambutis skambutis1 = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME1);
        Skambutis skambutis2 = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME1);
        Skambutis skambutis3 = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME2);
        skambutisRepository.save(skambutis1);
        skambutisRepository.save(skambutis2);
        skambutisRepository.save(skambutis3);
        Iterable<Skambutis> darbuotojai = skambutisRepository.findByTrukme(TRUKME1);
        assertNotNull(darbuotojai);
        List<Skambutis> result = StreamSupport.stream(darbuotojai.spliterator(), false).collect(Collectors.toList());
        assertEquals(2, result.size());
    }

    @Test
    void testFindByVardasAndTelNr() {
        Skambutis skambutis1 = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID1, TRUKME1);
        Skambutis skambutis2 = new Skambutis(ATSILIEPTA, LAIKAS2, DARBUOTOJOID1, TRUKME1);
        Skambutis skambutis3 = new Skambutis(ATSILIEPTA, LAIKAS1, DARBUOTOJOID2, TRUKME2);
        skambutisRepository.save(skambutis1);
        skambutisRepository.save(skambutis2);
        skambutisRepository.save(skambutis3);
        Iterable<Skambutis> darbuotojai = skambutisRepository.findByDarbuotojoIdAndSkambutis(DARBUOTOJOID1, LAIKAS1);
        assertNotNull(darbuotojai);
        List<Skambutis> result = StreamSupport.stream(darbuotojai.spliterator(), false).collect(Collectors.toList());
        assertEquals(1, result.size());
    }
}
