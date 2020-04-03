package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.IngredientConfiguratinHelper;

@Controller

public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
    private final IngredientConfiguratinHelper conf;

	public IngredientController(RecipeService recipeService, IngredientService ingredeientService, IngredientConfiguratinHelper conf) {
		this.recipeService = recipeService;
		this.ingredientService = ingredeientService;
		this.conf = conf;
	}
	
	@GetMapping
	@RequestMapping("/recipes/{recipeId}/ingredients")
	public String getList(@PathVariable String recipeId, Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_RECIPE, recipeService.findCommandById(Long.valueOf(recipeId)));
		return conf.getView(conf.INDEX_PAGE,false);
	}
	
	@GetMapping
	@RequestMapping("/recipes/{recipeId}/ingredients/{id}/show")
	public String getRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_ITEM,ingredientService.findIngredientsByRecipeId(new Long(recipeId), new Long(id)));
		return conf.getView(conf.SHOW, false);
	}
		

}
