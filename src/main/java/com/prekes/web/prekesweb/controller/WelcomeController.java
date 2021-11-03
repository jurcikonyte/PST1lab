package com.prekes.web.prekesweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
@Controller is used to mark classes as Spring MVC Controller.
@RestController is a convenience annotation that does nothing more than adding the @Controller and @ResponseBody annotations
*/

// MVC controller
@Controller
public class WelcomeController {
	
	// GET request
	// http://localhost:8080
	//@RequestMapping(value = "/", method= RequestMethod.GET)
	@GetMapping("/")
	public String showRootPage(ModelMap model) {
		// put value "ANONYMOUS" to model variable "name"
		// this model is passed to view
		model.put("name", "ANONYMOUS");
		return "welcome"; // view resolver /WEB-INF/jsp/welcome.jsp
	}
	
	// GET request with parameter name = Jonas
	// http://localhost:8080/welcome?name=Jonas
	@GetMapping("/welcome")
	public String showWelcomePage(@RequestParam String name, ModelMap model) {
		// method parameter name which gets value of parameter in request URI has annotation @RequestParam
		// put value of name from request parameter to model variable "name"
		// this model is passed to view
		model.put("name", name);
		return "welcome"; // view resolver /WEB-INF/jsp/welcome.jsp
	}
	
	// GET request
	// http://localhost:8080/welcomeResponseBody
	@GetMapping("/welcomeResponseBody") 			
	@ResponseBody
	public String showWelcomeResponseBodyPage() {
		return "Laba diena su vištiena."; // not view resolver, return plain text
	}
}
