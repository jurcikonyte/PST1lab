package com.prekes.web.prekesweb.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.model.Skambutis;

//@Param("role") and go to http://localhost:8080/prekesRestRepository/search/findByPavadinimas?pavadinimas=Pienas

@RepositoryRestResource(path="skambuciaiRestRepository", collectionResourceRel="skambuciai")
public interface SkambutisRestRepository extends PagingAndSortingRepository<Skambutis, Integer> {
	
	List<Darbuotojas> findBySkambutis(@Param("skambutis") String skambutis);
}
