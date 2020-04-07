package guru.springframework.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.MeasureUnitService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.IngredientConfiguratinHelper;

@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final IngredientConfiguratinHelper conf;
	private final MeasureUnitService uomService;

	public IngredientController(RecipeService recipeService, IngredientService ingredeientService, MeasureUnitService uomService, IngredientConfiguratinHelper conf) {
		this.recipeService = recipeService;
		this.ingredientService = ingredeientService;
		this.conf = conf;
		this.uomService = uomService;
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

	@GetMapping
	@RequestMapping("recipes/{recipeId}/ingredients/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model){
		model.addAttribute("ingredient", ingredientService.findIngredientsByRecipeId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", uomService.findAll());
		return conf.BASE_PATH+conf.FORM_NAME;
	}

	@PostMapping
	@RequestMapping("recipes/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveCommand(command);
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put(conf.RECIPE_ID, Long.toString(savedCommand.getRecipeId()));
		parameters.put(conf.INGREDIENT_ID, Long.toString(savedCommand.getId()));
		return conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_SHOW, parameters, true);
	}

	@GetMapping
	@RequestMapping("recipes/{recipeId}/ingredients/new")
	public String addIngredient(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findCommandById(new Long(recipeId));
		if (recipeCommand == null) 
			throw new RuntimeException("Recipe ID doesn't exist");
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		model.addAttribute(conf.MODEL_ATTRIBUTE_SINGLE_ITEM,ingredientCommand);
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		model.addAttribute(conf.MODEL_ATTRIBUTE_ITEM_UOM_LIST,uomService.findAll());
		String returnUrl = conf.BASE_PATH+conf.FORM_NAME; 
		return returnUrl;
	}
	
	@GetMapping
	@RequestMapping("recipes/{recipeId}/ingredients/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
		ingredientService.deleteById(new Long(recipeId), new Long(id));
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put(conf.RECIPE_ID, recipeId);
		return conf.getDynamicView(conf.PATH_RECIPE_INGREDIENTS, parameters, true);
	}


}
