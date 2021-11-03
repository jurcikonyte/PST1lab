package com.prekes.web.prekesweb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prekes.web.prekesweb.model.Darbuotojas;

public interface DarbuotojasRepository extends CrudRepository<Darbuotojas, Integer> {
	
	List<Darbuotojas> findByMiestas(String miestas);
	
	Darbuotojas findOneByVardas(String vardas);

    List<Darbuotojas> findByVardasAndMiestas(String vardas, String miestas);
}
