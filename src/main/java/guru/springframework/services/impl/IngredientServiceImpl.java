package guru.springframework.services.impl;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
	

	private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand toCommandConverter;
    private final IngredientCommandToIngredient fromCommandConverter;
    
    
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand toCommandConverter,
			IngredientCommandToIngredient fromCommandConverter) {
		this.recipeRepository = recipeRepository;
		this.toCommandConverter = toCommandConverter;
		this.fromCommandConverter = fromCommandConverter;
	}


	@Override
	public IngredientCommand findIngredientsByRecipeId(Long recipeId, Long ingredientId) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if(!optionalRecipe.isPresent()) {
			throw new RuntimeException("Recipe id not found!!!");
		}
		Recipe recipe = optionalRecipe.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> toCommandConverter.convert(ingredient)).findFirst();
		if(!ingredientCommandOptional.isPresent()) {
			throw new RuntimeException("Ingredient id not found!!!");
		}
		return ingredientCommandOptional.get();
	}
    
    

	/*@Override
	public Set<Ingredient> findAll() {
        Set<Ingredient> items = new HashSet<Ingredient>();
        repository.findAll().iterator().forEachRemaining(items::add);
        return items;
	}

	@Override
	public Ingredient findById(long id) {
	    Optional<Ingredient> optionaItem = repository.findById(id);
	    if(!optionaItem.isPresent()) {
	    	throw new RuntimeException("Ingredient Not Found!!!");
	    }
	    return optionaItem.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveCommand(IngredientCommand command) {
		Ingredient detachItem = fromCommandConverter.convert(command);
		Ingredient savedItem = repository.save(detachItem);
		log.debug("Saved Ingredient Id: "+savedItem.getId());
		return toCommandConverter.convert(savedItem);
	}

	@Override
	@Transactional
	public IngredientCommand findCommandById(Long id) {
		Optional<Ingredient> optionalItem = repository.findById(id);
		if (!optionalItem.isPresent()) {
			throw new RuntimeException("Ingredient not found!!!");
		}
		return toCommandConverter.convert(optionalItem.get());
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}*/

}
