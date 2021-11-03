package com.prekes.web.prekesweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.repository.SkambutisRepository;

//@Component
@Service
public class SkambutisService {

	@Autowired
    private SkambutisRepository repository;

    public List<Skambutis> findAll() {
    	return (List<Skambutis>) repository.findAll();
    }
    
    public Skambutis findById(int id) {
        return repository.findById(id).get();
    }

    public List<Skambutis> findByDarbuotojoId(int darbuotojoId) {
        return repository.findByDarbuotojoId(darbuotojoId);
    }
    public List<Skambutis> findByAtsiliepta(int atsiliepta) {
        return repository.findByAtsiliepta(atsiliepta);
    }
    public List<Skambutis> findByTrukme(int trukme) {
        return repository.findByTrukme(trukme);
    }
    
    public int findMaxId() {
    	Iterable<Skambutis> skambuciai = repository.findAll();
    	int max = 0;
    	for(Skambutis o : skambuciai) {
    		if(o.getId() > max) max = o.getId();
    	}
    	return max;
    }    
    public List<Skambutis> findByLaikas(String laikas) {
    	List<Skambutis> skambuciai = new ArrayList<Skambutis>();
    	for(Skambutis o : repository.findAll()) {
    		if(o.getLaikas().equals(laikas)) 
    			skambuciai.add(o);
    	}
        return skambuciai;
    }
    
    public void update (Skambutis p) {
    	repository.save(p);
    }
    
    public Skambutis add(Skambutis p) {
    	return repository.save(p);
    }
    
    public void deleteById(int id) {
    	repository.deleteById(id);	
    }
    
    public void delete(Skambutis p) {
    	repository.delete(p);	
    }
}
