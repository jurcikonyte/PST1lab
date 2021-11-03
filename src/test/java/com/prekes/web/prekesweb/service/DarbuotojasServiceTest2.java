package com.prekes.web.prekesweb.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.repository.SkambutisRepository;
import com.prekes.web.prekesweb.repository.DarbuotojasRepository;
import com.prekes.web.prekesweb.repository.SkambutisRepository;

/*
 * TODO
 * Paziureti TestCoverage rezultatus
 * Desinias peles mygtukas -> Coverage As - > JUnit test
 * arba
 * meniu mygtukas "zalas trikampis su zalia-raudona staciakampeliu"
 * arba
 * Meniu Run -> Coverage As - > JUnit test
 */


@ExtendWith(MockitoExtension.class)
class DarbuotojasServiceTest2 {


    private static final String VARDAS = "JONAS";
    private static final String MIESTAS = "VILNIUS";
    private static final int ATSILIEPTA = 1;
    private static final int DARBUOTOJOID = 1;
    private static final String LAIKAS = "10.10 10:10";
    private static final int TRUKME = 1;
    @Mock
    DarbuotojasRepository repository;
    @Mock
    SkambutisRepository skambutisRepository;
    @InjectMocks
    DarbuotojasService service;
    @InjectMocks
    SkambutisService skambutisService;

    @DisplayName("Test Find All")
    @Test
    void testFindAll() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        List<Darbuotojas> gydytojai = new ArrayList<>();
        gydytojai.add(Darbuotojas);
        when(repository.findAll()).thenReturn(gydytojai);
        List<Darbuotojas> found = service.findAll();
        verify(repository).findAll();
        assertEquals(1, found.size());
    }

//    @Test
//    void testFindSkambutisByGydytojoId() {
//        List<Skambutis> Skambutis = new ArrayList<>();
//        Skambutis.add(new Skambutis(ATSILIEPTA,LAIKAS, DARBUOTOJOID, TRUKME));
//        List<Skambutis> found = service.findSkambuciaiByDarbuotojoId(DARBUOTOJOID);
//        verify(repository).findAll();
//        assertNotNull(found);
//    }

    @Test
    void testFindById() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(Darbuotojas));
        Darbuotojas found = service.findById(1);
        verify(repository).findById(Mockito.anyInt());
        assertNotNull(found);
    }

    @Test
    void testFindByTitle() {
        List<Darbuotojas> gydytojai = new ArrayList<>();
        gydytojai.add(new Darbuotojas(VARDAS, MIESTAS));
        when(repository.findAll()).thenReturn(gydytojai);
        List<Darbuotojas> found = service.findByVardas("VARDAS");
        verify(repository).findAll();
        assertNotNull(found);
    }

    @Test
    void testUpdate() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        service.update(Darbuotojas);
        verify(repository).save(Mockito.any(Darbuotojas.class));
    }

    @Test
    void testAdd() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        when(repository.save(Mockito.any(Darbuotojas.class))).thenReturn(Darbuotojas);
        Darbuotojas added = service.add(Darbuotojas);
        verify(repository).save(Mockito.any(Darbuotojas.class));
        assertNotNull(added);
    }

    @Test
    void testDeleteById() {
        service.deleteById(1);
        verify(repository).deleteById(Mockito.anyInt());
        verify(repository).deleteById(1);
        verify(repository, never()).deleteById(2);
    }

    @Test
    void testDelete() {
        Darbuotojas Darbuotojas = new Darbuotojas(VARDAS, MIESTAS);
        service.delete(Darbuotojas);
        verify(repository).delete(Mockito.any(Darbuotojas.class));
    }
}
