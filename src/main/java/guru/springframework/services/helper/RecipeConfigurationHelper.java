package guru.springframework.services.helper;

import org.springframework.stereotype.Component;

import guru.springframework.services.helper.ServiceCofigurationHelper;

@Component
public class RecipeConfigurationHelper extends ServiceCofigurationHelper{
	
	public final String MODEL_ATTRIBUTE_SINGLE_ITEM = "recipe";
	public final String MODEL_ATTRIBUTE_ITEM_LIST = "recipes";
	public final String FORM_NAME = "recipeform";
	public final String SHOW= "show";
	public final String UPDATE = "update";
	public final String DELETE = "delete";
	public final String NEW = "new";
	public final String INDEX_PAGE = "index";
	public final String BASE_PATH = "recipes/";
	
	@Override
	public String getBasePath() {
		return this.BASE_PATH;
	}
}
