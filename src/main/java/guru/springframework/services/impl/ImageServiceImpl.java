package guru.springframework.services.impl;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.ImageService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;
	
		
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}



    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            recipe.setImage(byteObjects);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }
    }



	@Override
	public Byte[] getRecipeImage(Long recipeId) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()) {
        	throw new RuntimeException("Recipe ID not valid!!!");
        }
		return optionalRecipe.get().getImage();
	}
	
	

}
