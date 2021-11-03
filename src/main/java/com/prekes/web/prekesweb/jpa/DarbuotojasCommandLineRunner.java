package com.prekes.web.prekesweb.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.repository.DarbuotojasRepository;

// CommandLineRunner is invoked on application start

@Component
public class DarbuotojasCommandLineRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(DarbuotojasCommandLineRunner.class);
	
	@Autowired
	private DarbuotojasRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("~~~~~~ DarbuotojasCommandLineRunner ~~~~~~");
		repository.save(new Darbuotojas("Jonas", "Vilnius"));
		repository.save(new Darbuotojas("Petras", "Kaunas"));
		repository.save(new Darbuotojas("Maryte", "Vilnius"));
		repository.save(new Darbuotojas("Janina", "Klaipeda"));
		repository.save(new Darbuotojas("Ona", "Kaunas"));
		repository.save(new Darbuotojas("Antanas", "Klaipėda"));

		for (Darbuotojas o : repository.findAll()) {
			log.info(o.toString());
		}
	}
}
