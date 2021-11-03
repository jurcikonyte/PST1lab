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
import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.service.SkambutisService;
import com.prekes.web.prekesweb.service.DarbuotojasService;

/*
@Controller is used to mark classes as Spring MVC Controller.
@RestController is a convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
*/

@RequestMapping("/darbuotojai")
@RestController
public class DarbuotojasRestController {
	@Autowired
	DarbuotojasService service;
	
	@Autowired
	SkambutisService serviceSkambutis;
	
	// GET request localhost:8080/darbuotojai
	@GetMapping(produces = {"application/json"}) //@GetMapping("/darbuotojai")		
	public ResponseEntity<List<Darbuotojas>> darbuotojaiJson() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	// GET request localhost:8080/darbuotojai/2
	@GetMapping(path="/{darbuotojoId}", produces = {"application/json"})     
	public ResponseEntity<Darbuotojas> darbuotojasById(@PathVariable int darbuotojoId) {
		return new ResponseEntity<>(service.findById(darbuotojoId), HttpStatus.OK);
	}
	
	// GET request localhost:8080/darbuotojai/1/skambuciai
	@GetMapping(path="/{darbuotojoId}/skambuciai", produces = {"application/json"})
	public ResponseEntity<List<Skambutis>> findskambuciaiFordarbuotojas(@PathVariable int darbuotojoId) {
		return new ResponseEntity<>(service.findSkambuciaiByDarbuotojoId(darbuotojoId), HttpStatus.OK);
	}	
	
	// POST request
	// http://localhost:8080/darbuotojai/1/skambuciai	
	// POST request body example JSON: {"darbuotojoId":1,"prekesKodas":2,"vnt":10,"date":"2222-01-01","prekesPav":null}
	// Value of response header 'location' is set to uri of newly created source, 
	// e.g., http://localhost:8080/darbuotojai/1/skambuciai/1-2-2222-01-01
	@PostMapping(path="/{darbuotojoId}/skambuciai") 
	public ResponseEntity<Void> addSkambutisToDarbuotojas(@PathVariable String darbuotojoId, @RequestBody Skambutis newSkambutis) {

		// method parameter newSkambutis with annotation @RequestBody gets request body
		// request body originally contains JSON and Spring converts it to java object Skambutis
		Skambutis s = serviceSkambutis.add(newSkambutis);

		if (s == null)
			return ResponseEntity.noContent().build(); // status: 204 No content

		// if addition is Success, then return URI of new resource Skambutis in response header
		// URI = "/darbuotojai/{darbuotojoId}/skambuciai/{skambucioId}"
		// where skambucioId consists from three fields: darbuotojoId, prekesKodas, data.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(s.getId()).toUri();

		// return response with status="created" at "location": 
		return ResponseEntity.created(location).build();  // status: 201 Created
	}
	
	// GET request http://localhost:8080/darbuotojai/1/skambuciai/1-1-2021-09-01
	@GetMapping(path="/{darbuotojoId}/skambuciai/{skambucioId}") 
	public Skambutis retrieveDetailsForSkambutis(@PathVariable int darbuotojoId, @PathVariable int skambucioId) {
		return serviceSkambutis.findById(skambucioId);
	}
}
