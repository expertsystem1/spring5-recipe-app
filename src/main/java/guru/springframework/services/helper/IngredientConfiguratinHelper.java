package guru.springframework.services.helper;

import org.springframework.stereotype.Component;

import guru.springframework.services.helper.ServiceCofigurationHelper;

@Component
public class IngredientConfiguratinHelper extends ServiceCofigurationHelper{
	
	public final String MODEL_ATTRIBUTE_SINGLE_ITEM = "ingredient";
	public final String MODEL_ATTRIBUTE_SINGLE_RECIPE = "recipe";
	public final String MODEL_ATTRIBUTE_ITEM_LIST = "ingredients";
	public final String MODEL_ATTRIBUTE_ITEM_UOM_LIST = "uomList";
	public final String FORM_NAME = "ingredientform";
	public final String SHOW= "show";
	public final String UPDATE = "update";
	public final String DELETE = "delete";
	public final String NEW = "new";
	public final String INDEX_PAGE = "list";
	public final String LIST = "list";
	public final String BASE_PATH = "recipes/ingredients/";
	public final String RECIPE_ID = "{recipeId}";
	public final String INGREDIENT_ID = "{id}";
	public final String PATH_RECIPE_INGREDIENTS = "/recipes/"+RECIPE_ID+"/ingredients";
	public final String PATH_RECIPE_INGREDIENTS_NEW = "/recipes/"+RECIPE_ID+"/ingredients/new";
	public final String PATH_RECIPE_INGREDIENT_POST = "/recipes/"+RECIPE_ID+"/ingredient";
	public final String PATH_RECIPE_INGREDIENT_SHOW = "/recipes/"+RECIPE_ID+"/ingredients/"+INGREDIENT_ID+"/show";
	public final String PATH_RECIPE_INGREDIENT_UPDATE = "/recipes/"+RECIPE_ID+"/ingredients/"+INGREDIENT_ID+"/update";
		
	@Override
	public String getBasePath() {
		return this.BASE_PATH;
	}
}
