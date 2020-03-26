package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.repositories.RecipeRepository;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
	
	private final RecipeRepository repository;

	public RecipeController(RecipeRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping({"/","","index","index.html"})
	public String getRecipeList(Model model) {
		model.addAttribute("recipes",repository.findAll());
		return "recipes/index";
	}
		
}
