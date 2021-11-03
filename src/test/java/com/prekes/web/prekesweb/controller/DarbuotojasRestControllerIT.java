package com.prekes.web.prekesweb.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.prekes.web.prekesweb.PrekesWebApplication;
import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.service.DarbuotojasService;

//Part1: Initialize and launch SpringBoot application
//Full Spring application context is started but without the server
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@LocalServerPort
//private int port;

//Part2: Invoke url /darbuotojai/1/skambuciai/1-3-2021-09-02
//private TestRestTemplate template = new TestRestTemplate();


@SpringBootTest(classes = PrekesWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DarbuotojasRestControllerIT {
	@LocalServerPort
	private int port;
    
	@Autowired
	TestRestTemplate restTemplate; // Spring injects TestRestTemplate and knows the random number of port
	
	@Autowired
	DarbuotojasService service;
	
	private Darbuotojas darbuotojas;
	
	@BeforeEach
	void setUp() {
		darbuotojas = service.findAll().get(0);
	}
	
	@Test
	void test() {
		System.out.println("PORT=" + port);
	}

	// 1 budas, kai nenustatome requesto headeriu
	// TestRestTemplate will be deprecated in future versions,
	// better use WebTestClient (see example below)
	@Test
	void testRetrieveDetailsForSkambutis1() throws Exception {
		
		//String url = "http://localhost:" + port	+ "/darbuotojai/1/skambuciai/1-3-2021-09-02";
		String url = "/darbuotojai/1/skambuciai/1";

		// Invote GET request on url, receive response and convert it to object String.class or Skambutis.class
		String   responseAsString = restTemplate.getForObject(url, String.class);
		Skambutis responseAsObject = restTemplate.getForObject(url, Skambutis.class);
		
		System.out.println("RESPONSE_1:" + responseAsString);
		System.out.println("RESPONSE_1:" + responseAsObject);
		
		String expected = "{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":1 }";

		// JSONAssert is part of Spring Boot
		JSONAssert.assertEquals(expected, responseAsString, false);
	}

	// 2 budas, kai galime nustatyti requesto headerius
	// TestRestTemplate will be deprecated in future versions,
	// better use WebTestClient (see example below)
	@Test
	void testRetrieveDetailsForSkambutis2() throws Exception {
		//String url = "http://localhost:" + port	+ "/darbuotojai/1/skambuciai/1";
		String url = "/darbuotojai/1/skambuciai/1";
		
		// set value of request header to Accept: application/json
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		// create request with null body and given headers
		HttpEntity<String> entity = new HttpEntity<String>(null, headers); // request body is null
		
		// Invote GET request on url, receive response and convert it to object String.class or Skambutis.class
		ResponseEntity<String>   responseAsString = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		ResponseEntity<Skambutis> responseAsObject = restTemplate.exchange(url, HttpMethod.GET, entity, Skambutis.class);
		
		System.out.println("RESPONSE_2:" + responseAsString.getBody());
		System.out.println("RESPONSE_2:" + responseAsObject.getBody());
		
		String expected = "{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":1 }";

		// JSONAssert is part of Spring Boot
		JSONAssert.assertEquals(expected, responseAsString.getBody(), false);
	}
	
	// 3 budas
	// use WebTestClient
	@Test
	void testRetrieveDetailsForSkambutis() throws Exception {
		String expected = "{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":1 }";
		
		WebTestClient
		  .bindToServer()
		    .baseUrl("http://localhost:" + port)
		    .build()
		    .get()
		    .uri("/darbuotojai/1/skambuciai/1")
		  .exchange()
		    .expectStatus().isOk()			// 200
		    .expectHeader().valueEquals("Content-Type", "application/json")
		    .expectBody().json(expected);
	}

	// TestRestTemplate will be deprecated in future versions,
	// better use WebTestClient
	@Test
	void testFindSkambuciaiForDarbuotojas() {
		//String url = "http://localhost:" + port	+ "/darbuotojai/1/skambuciai";
		String url = "http://localhost:" + port + "/darbuotojai/1/skambuciai";
		
		// Set value of request header to Accept: application/json
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
		// Create request with null body and given headers
		HttpEntity<String> entity = new HttpEntity<String>(null, headers); // request body is null

		// Invote GET request on url:
		ResponseEntity<List<Skambutis>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
	                new ParameterizedTypeReference<List<Skambutis>>() {});

		assertEquals(200, response.getStatusCodeValue());
		
		System.out.println("RESPONSE_3:" + response.getBody());
			
		Skambutis sample = new Skambutis( 1, "01.02 09:15", 1, 1);

		assertTrue(response.getBody().contains(sample));
	}
	

	// TestRestTemplate will be deprecated in future versions,
	// better use WebTestClient (see example below)
	@Test
	void testAddSkambutisToDarbuotojas() {
		
		//String url = "http://localhost:" + port	+ "/darbuotojai/1/skambuciai";
		String url = "/darbuotojai/1/skambuciai";
		
		// Set value of request header to Accept: application/json
		HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Skambutis skam = new Skambutis( 1, "01.02 09:15", 1, 1);

		// Create request with given body and given headers
		HttpEntity<Skambutis> entity = new HttpEntity<Skambutis>(skam, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		assertEquals(201, response.getStatusCodeValue()); // 201 - created
		// get URI of new created resource from header
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0); 
		
		//"/darbuotojai/1/skambuciai/1-3-2021-09-02"
		System.out.println("NEW RESOURCE URI: " + actual); 
		
		assertTrue(actual.contains("/darbuotojai/1/skambuciai/1")); // skam.getPirkimasId()
	}
	
	// use WebTestClient
	@Test
	void testAddSkambutisToDarbuotojas1() {
		Skambutis skam = new Skambutis( 1, "01.02 09:15", 1, 1);
		
		WebTestClient
		  .bindToServer()
		    .baseUrl("http://localhost:" + port)
		    .build()
		    .post()
		    .uri("/darbuotojai/1/skambuciai").bodyValue(skam)
		  .exchange()
		    .expectStatus().isCreated()
		    .expectHeader().location("http://localhost:" + port + "/darbuotojai/1/skambuciai/1-2-2025-02-20");
	}	

	@Test
	void testDarbuotojaiJSON() throws Exception {
        String url = "http://localhost:" + port + "/darbuotojai-json";
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("RESPONSE:" + response.getBody());
        String expected = "[{\"id\":6,\"vardas\":\"Jonas\",\"miestas\":\"Vilnius\"}," +
                "{\"id\":7,\"vardas\":\"Petras\",\"miestas\":\"Kaunas\"}," +
                "{\"id\":8,\"vardas\":\"Maryte\",\"miestas\":\"Vilnius\"}," +
                "{\"id\":9,\"vardas\":\"Janina\",\"miestas\":\"Klaipeda\"}," +
                "{\"id\":10,\"vardas\":\"Ona\",\"miestas\":\"Kaunas\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	void testDarbuotojasById() throws Exception {
        String url = "http://localhost:" + port + "/darbuotojai/6";
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("RESPONSE:" + response.getBody());
        String expected = "{\"id\":6,\"vardas\":\"Jonas\",\"miestas\":\"Vilnius\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
    @Test
    void testAddDarbuotojas() {
        String url = "http://localhost:" + port + "/gydytojai";
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        Darbuotojas darbuotojas = new Darbuotojas("Onute", "Mazeikiai");
        darbuotojas.setId(20);
        HttpEntity<Darbuotojas> entity = new HttpEntity<>(darbuotojas, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        System.out.println("NEW RESOURCE URI: " + actual);
        assertTrue(actual.contains("/darbuotojai/21"));
    }

    @Test
    void testDeleteDarbuotojasById() {
        String url = "http://localhost:" + port + "/darbuotojai/6";
        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.delete(url, String.class);
    }
}
