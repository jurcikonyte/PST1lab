package com.prekes.web.prekesweb.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.repository.SkambutisRepository;

// CommandLineRunner is invoked on application start

@Component
public class SkambutisCommandLineRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(SkambutisCommandLineRunner.class);
	
	@Autowired
	private SkambutisRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("~~~~~~ SkambutisCommandLineRunner ~~~~~~");
		repository.save(new Skambutis( 1, "01.02 09:15", 1, 1));
		repository.save(new Skambutis( 0, "01.03 09:00", 2, 0));
		repository.save(new Skambutis( 1, "01.04 06:15", 3, 10));
		repository.save(new Skambutis( 1, "01.05 09:25", 1, 11));
		repository.save(new Skambutis( 0, "01.06 01:50", 2, 0));
		repository.save(new Skambutis( 1, "01.07 09:15", 1, 15));
		repository.save(new Skambutis( 0, "01.03 09:05", 4, 0));
		repository.save(new Skambutis( 1, "01.04 05:15", 5, 7));
		repository.save(new Skambutis( 0, "01.06 09:40", 2, 0));
		repository.save(new Skambutis( 1, "01.05 04:20", 3, 11));
		repository.save(new Skambutis( 0, "01.06 01:53", 4, 0));
		repository.save(new Skambutis( 1, "01.07 09:10", 3, 15));
		repository.save(new Skambutis( 0, "01.03 19:05", 1, 0));
		repository.save(new Skambutis( 1, "01.04 18:12", 4, 7));
		repository.save(new Skambutis( 0, "01.06 13:42", 3, 0));

		for (Skambutis o : repository.findAll()) {
			log.info(o.toString());
		}
	}

}
