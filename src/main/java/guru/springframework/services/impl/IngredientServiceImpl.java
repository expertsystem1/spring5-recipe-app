package guru.springframework.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.MeasureUnitRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{


	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand toCommandConverter;
	private final IngredientCommandToIngredient fromCommandConverter;
	private final MeasureUnitRepository unitOfMeasureRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand toCommandConverter,
			IngredientCommandToIngredient fromCommandConverter, MeasureUnitRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.toCommandConverter = toCommandConverter;
		this.fromCommandConverter = fromCommandConverter;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
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


	@Override
	public IngredientCommand saveCommand(IngredientCommand command) {
		Long recipeId = command.getRecipeId();
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (!optionalRecipe.isPresent()) {
			log.debug("Recipe ID not found!");
			return new IngredientCommand();
		}
		Recipe recipe = optionalRecipe.get();
		Long ingredientId = command.getId();
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
		if(!ingredientOptional.isPresent()) {
			Ingredient ingredient = fromCommandConverter.convert(command);
			ingredient.setRecipe(recipe);
			recipe.addIngredient(ingredient);
		}else {
			Ingredient ingredientFound = ingredientOptional.get();
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
					.findById(command.getUnitOfMeasure().getId())
					.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); 
		}
		Recipe savedRecipe = recipeRepository.save(recipe);
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
				.findFirst();
		if(!savedIngredientOptional.isPresent()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                    .findFirst();
		}
		return toCommandConverter.convert(savedIngredientOptional.get());
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
