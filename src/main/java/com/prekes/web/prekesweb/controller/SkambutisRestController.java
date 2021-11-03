package com.prekes.web.prekesweb.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.service.SkambutisService;

/*
@Controller is used to mark classes as Spring MVC Controller.
@RestController is a convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
*/

@RequestMapping("/skambuciai")
@RestController
public class SkambutisRestController {
	@Autowired
	SkambutisService service;
	
	// GET request
	// http://localhost:8080/skambuciai
	@GetMapping(produces = {"application/json"}) 
	public ResponseEntity<List<Skambutis>> skambuciaiJson() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK); // Spring converts java object to -> JSON
	}
	
	// GET request
	// http://localhost:8080/skambuciai/2
	@GetMapping(path="/{prekeId}", produces = {"application/json"})
	public ResponseEntity<Skambutis> SkambutisById(@PathVariable int skambutisId) {
		return new ResponseEntity<>(service.findById(skambutisId), HttpStatus.OK); // Spring converts java object to -> JSON
	}
	
	// POST request
	// http://localhost:8080/skambuciai	
	// POST request body example JSON: {"kodas":15,"pavadinimas":"CCC","salis":"LV","kainaVnt":0.15}
	// Value of response header 'location' is set to uri of newly created source, e.g., http://localhost:8080/skambuciai/15
	@PostMapping()
	public ResponseEntity<Void> addSkambutis(@RequestBody Skambutis newSkambutis) {
		
		// method parameter newskambutis with annotation @RequestBody gets request body
		// request body originally contains JSON and Spring converts it to java object skambutis
		Skambutis s = service.add(newSkambutis); 

		if (s == null)
			return ResponseEntity.noContent().build();

		// if addition is Success, then return URI of new resource skambutis in response header
		// URI = "/skambutis/{skambutisId}"
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(s.getId()).toUri();

		// return response with status="created" at "location": 
		return ResponseEntity.created(location).build(); 
	}	
}
