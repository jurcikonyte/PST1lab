package com.prekes.web.prekesweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.repository.DarbuotojasRepository;

@Service
public class DarbuotojasService {

	@Autowired
	SkambutisService skambutisService;

	@Autowired
	private DarbuotojasRepository repository; 
	
    public List<Darbuotojas> findAll() {
        return (List<Darbuotojas>)repository.findAll();
    }
    
    public Darbuotojas findById(int darbuotojoId) {
    	return repository.findById(darbuotojoId).get();
    }
    public List<Darbuotojas> findByVardas(String vardas) {
        List<Darbuotojas> d = new ArrayList<>();
        for (Darbuotojas o : repository.findAll()) {
            if (o.getVardas().equals(vardas))
                d.add(o);
        }
        return d;
    }
    
    public List<Skambutis> findSkambuciaiByDarbuotojoId(int darbuotojoId) {
        return skambutisService.findByDarbuotojoId(darbuotojoId);
    }
    
    public int findMaxId() {
    	Iterable<Darbuotojas> darbuotojai = repository.findAll();
    	int max = 0;
    	for(Darbuotojas o : darbuotojai) {
    		if(o.getId() > max) max = o.getId();
    	}
    	return max;
    }
    
    public void update (Darbuotojas d) {
    	repository.save(d);
    }
    
    public Darbuotojas add(Darbuotojas d) {
    	return repository.save(d);
    }
    
    public void deleteById(int id) {
    	repository.deleteById(id);	
    }
    
    public void delete(Darbuotojas d) {
    	repository.delete(d);	
    }

}
