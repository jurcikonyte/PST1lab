package com.prekes.web.prekesweb.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.service.DarbuotojasService;
import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.service.SkambutisService;

/*
@Controller is used to mark classes as Spring MVC Controller.
@RestController is a convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
*/

@Controller
public class DarbuotojasController {
	@Autowired
	DarbuotojasService service;
	
	@Autowired
	SkambutisService serviceSkambutis;
	
	// GET request
	// localhost:8080/list-darbuotojai
	@GetMapping("/list-darbuotojai")		
	public String showAll(ModelMap model) {
		model.put("darbuotojai", service.findAll());
		return "list-darbuotojai";
	}
	
	@GetMapping("/add-darbuotojas")
	public String showAddPage(ModelMap model) {
		model.addAttribute("darbuotojas", new Darbuotojas("", ""));
		return "darbuotojas";
	}

	@PostMapping("/add-darbuotojas")
	public String add(ModelMap model, @ModelAttribute("darbuotojas") Darbuotojas zm, BindingResult result) {
		if(result.hasErrors()) {
			return "darbuotojas";
		}
		service.add(zm);
		return "redirect:/list-darbuotojai";
	}

	@GetMapping("/update-darbuotojas/{zmogausId}")
	public String showUpdatePage(ModelMap model, @PathVariable int zmogausId) {
		model.addAttribute("darbuotojas", service.findById(zmogausId));
		return "darbuotojas";
	}

	@PostMapping("/update-darbuotojas/{zmogausId}")
	public String update(ModelMap model, @ModelAttribute("darbuotojas") Darbuotojas zm, BindingResult result) {
		if(result.hasErrors()) {
			return "darbuotojas";
		}
		service.update(zm);
		return "redirect:/list-darbuotojai";
	}
	
	@GetMapping("/delete-darbuotojas/{zmogausId}")
	public String delete(@PathVariable int zmogausId) {
		service.deleteById(zmogausId);
		return "redirect:/list-darbuotojai";
	}
	
}
