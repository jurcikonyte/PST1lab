package com.prekes.web.prekesweb.controller;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.service.DarbuotojasService;
import com.prekes.web.prekesweb.service.SkambutisService;

//only darbuotojasController context is needed for this test, not the whole application
@WebMvcTest(value = DarbuotojasController.class) 
class DarbuotojasControllerTest {

	// MockMvc provides support for Spring MVC testing. It prepares a fake web
	// application context to mock the HTTP requests and responses.
	// It encapsulates all web application beans and makes them available for
	// testing.
	// Allows to make a call to the service.
	@Autowired
	private MockMvc mockMvc;

	// WebApplicationContext provides a web application configuration. It loads all
	// the application beans and controllers into the context.
	@Autowired
	private WebApplicationContext webApplicationContext;

	// use @MockBean to add mock objects to the Spring application context.
	@MockBean
	private DarbuotojasService darbuotojasService;

	@MockBean
	private SkambutisService skambutisService;

	// Verify that WebApplicationContext object is loaded properly.
	// Verify that the right servletContext is being attached.
	// Verify that controller bean exists in web context.
	@Test
	public void givenWac_whenServletContext_thenItProvidesController() {
		ServletContext servletContext = webApplicationContext.getServletContext();

		assertNotNull(servletContext);
		assertTrue(servletContext instanceof MockServletContext);
		assertNotNull(webApplicationContext.getBean("darbuotojasController")); // pirma kontrolerio pav-mo raide mazoji
	}

	@Test
	public void testShowAll() throws Exception {
		List<Darbuotojas> l = new ArrayList<Darbuotojas>();
		l.add(new Darbuotojas("Jonas", "Vilnius"));
		l.add(new Darbuotojas("Petras", "Kaunas"));
		when(darbuotojasService.findAll()).thenReturn(l);

		RequestBuilder rb = MockMvcRequestBuilders.get("/list-darbuotojai").accept(MediaType.TEXT_HTML);

		MvcResult result = mockMvc.perform(rb)
				.andExpect(MockMvcResultMatchers.status().isOk()) // 200
				.andExpect(MockMvcResultMatchers.view().name("list-darbuotojai"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/list-darbuotojai.jsp"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("darbuotojai"))
				.andReturn();
	}

	@Test
    public void testShowAddPage() throws Exception {
		RequestBuilder rb = MockMvcRequestBuilders.get("/add-darbuotojas");

        MvcResult result = mockMvc.perform(rb)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("darbuotojas"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/darbuotojas.jsp"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("darbuotojas"))
				.andExpect(MockMvcResultMatchers.model().attribute("darbuotojas",  hasProperty("vardas", emptyOrNullString())))
				.andExpect(MockMvcResultMatchers.model().attribute("darbuotojas",  hasProperty("miestas", emptyOrNullString())))
				.andReturn();
    }
	
	@Test
	void testAdd() throws Exception {

		Darbuotojas o = new Darbuotojas("Jonas", "Vilnius");
		when(darbuotojasService.add(Mockito.any(Darbuotojas.class))).thenReturn(o);

		// Send POST request
		// Pass @ModelAttribute darbuotojas object with flashAttr()
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/add-darbuotojas")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("vardas", "Jonas")
				.param("miestas", "Vilnius")
				.flashAttr("darbuotojas", new Darbuotojas("Jonas", "Vilnius"));

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isFound()) // 302 -	status found after redirect
				.andExpect(MockMvcResultMatchers.redirectedUrl("/list-darbuotojai")) // .andExpect(MockMvcResultMatchers.view().name("redirect:/list-darbuotojai"))
				.andReturn();
		
		verify(darbuotojasService).add(Mockito.any(Darbuotojas.class)); // verifying mock call 1 time
	}

	@Test
    public void testShowUpdatePage() throws Exception {
		when(darbuotojasService.findById(Mockito.anyInt())).thenReturn(new Darbuotojas("AAA", "Admin"));
		
		RequestBuilder rb = MockMvcRequestBuilders.get("/update-darbuotojas/1");

        MvcResult result = mockMvc.perform(rb)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("darbuotojas"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/darbuotojas.jsp"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("darbuotojas"))
				.andExpect(MockMvcResultMatchers.model().attribute("darbuotojas",  hasProperty("vardas", Matchers.equalTo("AAA"))))
				.andExpect(MockMvcResultMatchers.model().attribute("darbuotojas",  hasProperty("role", Matchers.equalTo("Admin"))))
				.andReturn();
    }
	
	@Test
	void testUpdate() throws Exception {
		// Send POST request
		// Pass @ModelAttribute darbuotojas object with flashAttr()
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/update-darbuotojas/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("vardas", "Jonas")
				.param("miestas", "Vilnius")
				.flashAttr("darbuotojas", new Darbuotojas("Jonas", "Vilnius"));

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isFound()) // 302 -	status found after redirect
				.andExpect(MockMvcResultMatchers.redirectedUrl("/list-darbuotojai")) // .andExpect(MockMvcResultMatchers.view().name("redirect:/list-darbuotojai"))
				.andReturn();
		
		verify(darbuotojasService).update(Mockito.any(Darbuotojas.class)); // verifying mock call 1 time
	}
	
	@Test
	void testDelete() throws Exception {
		// Send POST request
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delete-darbuotojas/1");

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isFound()) // 302 -	status found after redirect
				.andExpect(MockMvcResultMatchers.redirectedUrl("/list-darbuotojai")) // .andExpect(MockMvcResultMatchers.view().name("redirect:/list-darbuotojai"))
				.andReturn();
		
		verify(darbuotojasService).deleteById(Mockito.anyInt()); // verifying mock call 1 time
	}
}
