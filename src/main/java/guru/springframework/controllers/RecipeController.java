package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.commands.RecipeCommand;
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
	
	@RequestMapping("/{id}/show")
	public String showRecipeById(@PathVariable String id, Model model) {
		model.addAttribute("recipe",service.getRecipeById(new Long(id)));
		return "recipes/show";
	}
	
	@RequestMapping("/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe",service.findCommandById(new Long(id)));
		return "recipes/recipeform";
	}
	
	@RequestMapping("/{id}/delete")
	public String deleteRecipe(@PathVariable String id, Model model) {
		service.deleteById(new Long(id));
		return "redirect:/recipes/";
	}
	
	@RequestMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipes/recipeform";
	}
	
	@PostMapping({"/",""})
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = service.saveRecipeCommand(command);
		return "redirect:/recipes/"+savedCommand.getId()+"/show/";
	}
	

		
}
