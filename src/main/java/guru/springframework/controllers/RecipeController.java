package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
	
	private final RecipeService service;

	public RecipeController(RecipeService service) {
		this.service = service;
	}
	
	@RequestMapping({"/","","index","index.html"})
	public String getRecipeList(Model model) {
		model.addAttribute("recipes",service.getRecipes());
		return "recipes/index";
	}
		
}
