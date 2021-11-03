package com.prekes.web.prekesweb.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.*;

import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.service.DarbuotojasService;
import com.prekes.web.prekesweb.service.SkambutisService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SkambutisController.class) // only skambutisControllerTest context is needed for this test, not the whole application
class SkambutisControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	SkambutisService skambutisService;
	
	@MockBean
	DarbuotojasService darbuotojasService;
	
	@InjectMocks
	SkambutisController skambutisController;
	
	@Test
	void testShowAll() throws Exception {
		List<Skambutis> l = new ArrayList<Skambutis>();
		l.add(new Skambutis( 1, "01.02 09:15", 1, 1));
		l.add(new Skambutis( 1, "01.20 09:50", 2, 10));
		when(skambutisService.findAll()).thenReturn(l);
		
		RequestBuilder rb = MockMvcRequestBuilders
				.get("/list-skambuciai")
				.accept(MediaType.TEXT_HTML);
				
		MvcResult result = mockMvc.perform(rb)
        				.andExpect(MockMvcResultMatchers.status().isOk()) 
        				.andExpect(MockMvcResultMatchers.view().name("list-skambuciai"))
        				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/list-skambuciai.jsp"))
        				.andExpect(MockMvcResultMatchers.model().attributeExists("skambuciai"))
        				.andReturn();
	}
	
	@Test
    public void testShowAddPage() throws Exception {
		RequestBuilder rb = MockMvcRequestBuilders.get("/add-skambutis");

        MvcResult result = mockMvc.perform(rb)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("skambutis"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/skambutis.jsp"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("skambutis"))
				.andExpect(MockMvcResultMatchers.model().attribute("skambutis",  hasProperty("id", notNullValue())))
				.andReturn();
    }
	
	@Test
    void testAdd() throws Exception {
		when(skambutisService.add(Mockito.any(Skambutis.class))).thenReturn(new Skambutis( 1, "01.02 09:15", 1, 1));
	
		RequestBuilder rb = MockMvcRequestBuilders
				.post("/add-skambutis")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("atsiliepta", "1")		
                .param("laikas", "01.02 09:15")
                .param("darbuotojoId", "1")
                .param("trukme", "1")
                .flashAttr("skambutis", new Skambutis());
    
		mockMvc.perform(rb)
        .andExpect(MockMvcResultMatchers.status().isFound()) 
        .andExpect(MockMvcResultMatchers.view().name("redirect:/list-skambutiss"));
        
		verify(skambutisService).add(Mockito.any(Skambutis.class)); 
    }

    @Test
    void testShowUpdatePage() throws Exception {
		when(skambutisService.findById(Mockito.anyInt())).thenReturn(new Skambutis( 1, "01.02 09:15", 1, 1));
		
		RequestBuilder rb = MockMvcRequestBuilders
				.get("/update-skambutis/0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("skambuciai", new Skambutis());
        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("skambuciai"));
    }

    @Test
    void testUpdate() throws Exception {
    	when(skambutisService.findById(Mockito.anyInt())).thenReturn(new Skambutis( 1, "01.02 09:15", 1, 1));        RequestBuilder rb = MockMvcRequestBuilders
    			.post("/update-skambutis/0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("atsiliepta", "1")		
                .param("laikas", "01.02 09:15")
                .param("darbuotojoId", "1")
                .param("trukme", "1")
                .flashAttr("skambutis", new Skambutis());
    	
		mockMvc.perform(rb)
        .andExpect(MockMvcResultMatchers.status().isFound()) 
        .andExpect(MockMvcResultMatchers.view().name("redirect:/list-skambutiss"));
        
		verify(skambutisService).add(Mockito.any(Skambutis.class));
    }

    @Test
    void testDelete() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/delete-skambuciai/0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/list-skambuciai"));
        verify(skambutisService).deleteById(Mockito.anyInt());
    }
}

