package guru.springframework.services.helper;

import org.springframework.stereotype.Service;

@Service
public class ImageConfigurationHelper extends ServiceCofigurationHelper {
	
    public final String UPLOAD_FORM = "imageuploadform";
	public final String MODEL_ATTRIBUTE_SINGLE_RECIPE = "recipe";
	public final String BASE_PATH = "recipes/";
	public final String PATH_UPLOAD_FORM ="/recipes/{recipeId}/image";
	public final String PATH_RECIPE_SHOW ="/recipes/{recipeId}/show";
	public final String RECIPE_ID = "{recipeId}";
}
