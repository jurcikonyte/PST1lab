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
class SkambutisServiceTest {
	
	  	private static final int ATSILIEPTA = 1;
	    private static final String LAIKAS = "10.10 10:10";
	    private static final int DARBUOTOJOID = 1;
	    private static final int TRUKME = 1;
	    @Mock
	    SkambutisRepository repository;
	    @InjectMocks
	    SkambutisService service;

	    @DisplayName("Test Find All")
	    @Test
	    void testFindAll() {
	        Skambutis skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
	        List<Skambutis> Skambutis = new ArrayList<>();
	        Skambutis.add(skambutis);
	        when(repository.findAll()).thenReturn(Skambutis);
	        List<Skambutis> found = service.findAll();
	        verify(repository).findAll();
	        assertEquals(1, found.size());
	    }

	    @Test
	    void testFindById() {
	        Skambutis skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
	        when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(skambutis));
	        Skambutis found = service.findById(1);
	        verify(repository).findById(Mockito.anyInt());
	        assertNotNull(found);
	    }

	    @Test
	    void testFindBy() {
	        List<Skambutis> skambutis = new ArrayList<>();
	        skambutis.add(new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME));
	        List<Skambutis> found = service.findByLaikas(LAIKAS);
	        verify(repository).findByLaikas(LAIKAS);
	        assertNotNull(found);
	        List<Skambutis> found1 = service.findByAtsiliepta(ATSILIEPTA);
	        assertNotNull(found1);
	        verify(repository).findByAtsiliepta(ATSILIEPTA);
	        List<Skambutis> found2 = service.findByDarbuotojoId(DARBUOTOJOID);
	        verify(repository).findByDarbuotojoId(DARBUOTOJOID);
	        assertNotNull(found2);
	        List<Skambutis> found3 = service.findByTrukme(TRUKME);
	        verify(repository).findByTrukme(TRUKME);
	        assertNotNull(found3);
	    }

	    @Test
	    void testFindMaxId() {
	        List<Skambutis> skambuciai = new ArrayList<>();
	        Skambutis skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
	        skambutis.setId(5);
	        skambuciai.add(skambutis);
	        when(repository.findAll()).thenReturn(skambuciai);
	        int found = service.findMaxId();
	        assertEquals(5, found);
	    }

	    @Test
	    void testUpdate() {
	        Skambutis skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
	        service.update(skambutis);
	        verify(repository).save(Mockito.any(Skambutis.class));
	    }

	    @Test
	    void testAdd() {
	        Skambutis skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
	        when(repository.save(Mockito.any(Skambutis.class))).thenReturn(skambutis);

	        Skambutis added = service.add(skambutis);
	        verify(repository).save(Mockito.any(Skambutis.class));
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
	        Skambutis skambutis = new Skambutis(ATSILIEPTA, LAIKAS, DARBUOTOJOID, TRUKME);
	        service.delete(skambutis);
	        verify(repository).delete(Mockito.any(Skambutis.class));
	    }

}
