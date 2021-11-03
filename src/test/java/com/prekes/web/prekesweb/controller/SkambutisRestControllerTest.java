package com.prekes.web.prekesweb.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.service.DarbuotojasService;
import com.prekes.web.prekesweb.service.SkambutisService;

@WebMvcTest(value = SkambutisRestController.class)
class SkambutisRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	// use @MockBean to add mock objects to the Spring application context.
	@MockBean
	SkambutisService skambutisService; 
	
	@MockBean
	DarbuotojasService darbuotojasService; 
	
	// @MockBean PrekeService service is a spring component
	// Mockito doesn't know if service was used in test and doesn't clean mock object after each test
	// therefore you need to reset service after each test:  
	@AfterEach
	void tearDown() {
		reset(skambutisService); // Mockito resets object
		reset(darbuotojasService);
	}
	
	@Test
	void testShowSkambuciaiList() throws Exception {
		List<Skambutis> l = new ArrayList<Skambutis>();
		l.add(new Skambutis( 1, "01.02 09:15", 1, 1));
		l.add(new Skambutis( 1, "01.21 10:15", 2, 19));

		//1) setup:
        when(skambutisService.findAll()).thenReturn(l);

		//2) invocation:
		//send GET request to url "/prekes" and accept JSON response
        RequestBuilder rb = MockMvcRequestBuilders
				.get("/skambuciai")
				.accept(MediaType.APPLICATION_JSON);
		//MvcResult result = mockMvc.perform(rb).andReturn();
        MvcResult result = mockMvc.perform(rb)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		//3) verify:
        String expected = "["
                + "{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":1 },"
                + "{\"id\":2,\"atsiliepta\":1,\"laikas\":\"01.21 10:15\",\"darbuotojoId\":2, \"trukme\":19}"        		
                + "]";
        
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
    @Test
    void testShowSkambuciaiListById() throws Exception {
        Skambutis skambutis1 = new Skambutis( 1, "01.21 10:15", 2, 19);
        when(skambutisService.findById(skambutis1.getId())).thenReturn(skambutis1);
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/skambutis/" + skambutis1.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected ="{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":1 },";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void testAddSkambutis() throws Exception {
    	Skambutis skambutis1 = new Skambutis( 1, "01.21 10:15", 2, 19);
        when(skambutisService.add(Mockito.any(Skambutis.class))).thenReturn(skambutis1);
        String skambutisJson = "{\"id\":1,\"atsiliepta\":1,\"laikas\":\"01.02 09:15\",\"darbuotojoId\":1,\"trukme\":1 },";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/skambuciai")
                .accept(MediaType.APPLICATION_JSON)
                .content(skambutisJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/skambuciai/" + skambutis1.getId(), response.getHeader(HttpHeaders.LOCATION));

    }

    @Test
    void testDeleteSkambutisById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/skambutis/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
