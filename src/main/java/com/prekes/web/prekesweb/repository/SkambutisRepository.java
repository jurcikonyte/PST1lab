package com.prekes.web.prekesweb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prekes.web.prekesweb.model.Skambutis;

public interface SkambutisRepository extends CrudRepository<Skambutis, Integer> {
	
	List<Skambutis> findByTrukme(int trukme);
	
	List<Skambutis> findByDarbuotojoId(int darbuotojoId);
	
	List<Skambutis> findByAtsiliepta(int atsiliepta);
	
	List<Skambutis> findByLaikas(String laikas);
	
	Skambutis findOneBySkambutis(String skambutis);
	
	 List<Skambutis> findByDarbuotojoIdAndSkambutis(int darbuotojoId, String skambutis);
	
}
