package com.prekes.web.prekesweb.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.service.SkambutisService;
import com.prekes.web.prekesweb.service.DarbuotojasService;

//Narrow tests to only the web layer by using @WebMvcTest annotation
//Only ZmogusRestController context is needed for this test, not the whole application
@WebMvcTest(value = DarbuotojasRestController.class) 
class DarbuotojasRestControllerTest {
	// MockMvc provides support for Spring MVC testing. It prepares a fake web application context to mock the HTTP requests and responses. 
	// It encapsulates all web application beans and makes them available for testing.
	// Allows to make a call to the service.
	@Autowired
	private MockMvc mockMvc; 
	
	//WebApplicationContext provides a web application configuration. It loads all the application beans and controllers into the context.
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	// use @MockBean to add mock objects to the Spring application context. 
	@MockBean
	private DarbuotojasService darbuotojasService;
	
	@MockBean
	private SkambutisService skambutisService;

	// @MockBean darbuotojasService and SkambutisService are spring components
	// Mockito doesn't know if these services were used in test and doesn't clean mock objects after each test
	// therefore you need to reset these services after each test:
	@AfterEach void tearDown() { 
		reset(darbuotojasService); 	// Mockito resets object
		reset(skambutisService); // Mockito resets object 
	}
		
	// Verify that WebApplicationContext object is loaded properly. 
	// Verify that the right servletContext is being attached.
	// Verify that controller bean exists in web context.
	@Test
	public void givenWac_whenServletContext_thenItProvidesController() {
	    ServletContext servletContext = webApplicationContext.getServletContext();
	    
	    assertNotNull(servletContext);
	    assertTrue(servletContext instanceof MockServletContext);
	    assertNotNull(webApplicationContext.getBean("darbuotojasRestController")); // pirma kontrolerio pav-mo raide mazoji
	}
	
	@Test
	void testDarbuotojaiJson() throws Exception {
		List<Darbuotojas> l = new ArrayList<Darbuotojas>();
		l.add(new Darbuotojas("AAA", "Alytus"));
		l.add(new Darbuotojas("BBB", "Birzai"));
		when(darbuotojasService.findAll()).thenReturn(l);
		
		RequestBuilder rb = MockMvcRequestBuilders
				.get("/darbuotojai")
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(rb)
        				.andExpect(MockMvcResultMatchers.status().isOk()) // 200
        				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        				.andReturn();

		String expected = "[{\"vardas\":\"AAA\",miestas:\"Alytus\"},{\"vardas\":\"BBB\",miestas:\"Birzai\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}

	@Test
	void testDarbuotojasById() throws Exception {
		Darbuotojas d = new Darbuotojas("AAA", "Alytus");
		when(darbuotojasService.findById(Mockito.anyInt())).thenReturn(d);
		
		RequestBuilder rb = MockMvcRequestBuilders
				.get("/darbuotojai")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(rb)
        				.andExpect(MockMvcResultMatchers.status().isOk())
        				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        				.andReturn();

		String expected = "{\"vardas\":\"AAA\",miestas:\"Alytus\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	void testFindSkambuciaiForDarbuotojas() throws Exception {
        List<Skambutis> mockList = Arrays.asList(
        		new Skambutis( 1, "01.02 09:15", 1, 50),
                new Skambutis( 0, "01.20 09:15", 2, 0)
                );

        when(darbuotojasService.findSkambuciaiByDarbuotojoId(Mockito.anyInt())).thenReturn(mockList);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/darbuotojai/1/skambuciai")
                                .accept(MediaType.APPLICATION_JSON))
                				.andExpect(MockMvcResultMatchers.status().isOk())
                				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                				.andReturn();

        String expected = "["
                +"{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":50 }"
                + "{\"id\":2,\"atsiliepta\":0,\"laikas\":\"01.20 09:15\",\"darbuotojoId\":2,\"trukme\":0 }"
                + "]";
        
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}

	@Test
	void testAddSkambutisToZmogus() throws Exception {
		
		Skambutis mockSkambutis = new Skambutis( 1, "01.02 09:15", 1, 50);
		when(skambutisService.add(Mockito.any(Skambutis.class))).thenReturn(mockSkambutis);

		String SkambutisJson = "{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":50 }";
		
		//Send Skambutis as POST request body to /zmones/1/pirkimai
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/darbuotojai/1/skambuciai")
				//.accept(MediaType.APPLICATION_JSON)
				.content(SkambutisJson)					  // !!! sending content in POST request body
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// check the response status
		assertEquals(HttpStatus.CREATED.value(), response.getStatus()); // created 201

		//now check the response header
		// pirkimoId = "zmogausId-prekesKodas-data"
		assertEquals("http://localhost/zmones/1/pirkimai" + mockSkambutis.getId(), response.getHeader(HttpHeaders.LOCATION));
    
	}

	@Test
	void testRetrieveDetailsForSkambutis() throws Exception {
		
        Skambutis mockSkambutis = new Skambutis( 1, "01.02 09:15", 1, 50);
        when(skambutisService.findById(Mockito.anyInt())).thenReturn(mockSkambutis);
        RequestBuilder rb = MockMvcRequestBuilders.get("/darbuotojai/1/skambutis/" + mockSkambutis.getId()).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();

		//3) verify:		
		String expected ="{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":50 }";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}

}
