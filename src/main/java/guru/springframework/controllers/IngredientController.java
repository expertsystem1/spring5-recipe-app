package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.IngredientConfiguratinHelper;

@Controller

public class IngredientController {
	
	private final RecipeService recipeService;
    private final IngredientConfiguratinHelper conf;

	public IngredientController(RecipeService recipeService, IngredientConfiguratinHelper conf) {
		this.recipeService = recipeService;
		this.conf = conf;
	}
	
	@GetMapping
	@RequestMapping("/recipes/{recipeId}/ingredients")
	public String getList(@PathVariable String recipeId, Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_RECIPE, recipeService.findCommandById(Long.valueOf(recipeId)));
		return conf.getView(conf.INDEX_PAGE,false);
	}
		

}
