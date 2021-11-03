package com.prekes.web.prekesweb.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.prekes.web.prekesweb.model.Darbuotojas;
import com.prekes.web.prekesweb.model.Skambutis;
import com.prekes.web.prekesweb.service.DarbuotojasService;
import com.prekes.web.prekesweb.service.SkambutisService;

//MVC controller
@Controller
public class SkambutisController {
	
	@Autowired
	SkambutisService service;
	
	@Autowired
	DarbuotojasService darbuotojasService;
	
	// http://localhost:8080/list-prekes
	@GetMapping("/list-skambuciai")
	public String showAll(ModelMap model) {
		List<Skambutis> skambuciai = service.findAll();
        model.put("skambuciai", skambuciai);
        Map<Integer, Darbuotojas> darbuotojai = skambuciai.stream()
                .collect(Collectors.toMap(Skambutis::getId, d -> darbuotojasService.findById(d.getDarbuotojoId())));
        model.put("darbuotojai", darbuotojai);
		return "list-skambuciai"; // view resolver /WEB-INF/jsp/list-skambuciai.jsp
	}
	
	// http://localhost:8080/add-skambutis
	@GetMapping("/add-skambutis")
	public String showAddPage(ModelMap model) {
		model.addAttribute("skambutis", new Skambutis());
		return "skambutis";
	}

	// http://localhost:8080/add-skambutis
	@PostMapping("/add-skambutis")
	public String add(ModelMap model, @ModelAttribute("skambutis") Skambutis p, BindingResult result) {
		if(result.hasErrors()) {
			return "skambutis";
		}
		service.add(p);
		return "redirect:/list-skambuciai";
	}

	// http://localhost:8080/update-skambutis/1
	@GetMapping("/update-skambutis/{skambutisId}")
	public String showUpdatePage(ModelMap model, @PathVariable int skambutisId) {
		model.addAttribute("skambutis", service.findById(skambutisId));
		return "skambutis";
	}

	// http://localhost:8080/update-skambutis/1
	@PostMapping("/update-skambutis/{skambutisId}")
	public String update(ModelMap model, @ModelAttribute("skambutis") Skambutis p, BindingResult result) {
		if(result.hasErrors()) {
			return "skambutis";
		}
		service.update(p);
		return "redirect:/list-skambuciai";
	}
	
	// http://localhost:8080/delete-skambutis/1
	@GetMapping("/delete-skambutis/{skambutisId}")
	public String delete(@PathVariable int skambutisId) {
		service.deleteById(skambutisId);
		return "redirect:/list-skambuciai";
	}
}
