package com.example.eneasdev.projetoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
public class ProjetowebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetowebApplication.class, args);
	}

	@Controller
	static class HomeController {

		@GetMapping("/")
		public ModelAndView home() {
			return new ModelAndView("index");
		}
	}

}
