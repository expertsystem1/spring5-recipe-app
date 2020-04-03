package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {
	
	private final RecipeService service;

	public RecipeController(RecipeService service) {
		this.service = service;
	}
	
	@RequestMapping({"/","","index","index.html"})
	public String getRecipeList(Model model) {
		log.debug("Recipes list request");
		model.addAttribute("recipes",service.getRecipes());
		return "recipes/index";
	}
	
	@RequestMapping("/show/{id}")
	public String showRecipeById(@PathVariable String id, Model model) {
		model.addAttribute("recipe",service.getRecipeById(new Long(id)));
		return "recipes/show";
	}
		
}
