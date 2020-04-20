package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
    public Set<Recipe> findAll() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

	@Override
	public Recipe findById(long id) {
	    Optional<Recipe> recipeOptional = recipeRepository.findById(id);
	    if(!recipeOptional.isPresent()) {
	    	throw new NotFoundException("Recipe not found for ID value: "+ id);
	    }
	    return recipeOptional.get();
	}

	@Override
	@Transactional
	public RecipeCommand saveCommand(RecipeCommand command) {
		Recipe detachRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachRecipe);
		log.debug("Saved RecipeId: "+savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}
	
	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
		if (!optionalRecipe.isPresent()) {
			throw new RuntimeException("Recipe not found!!!");
		}
		return recipeToRecipeCommand.convert(optionalRecipe.get());
	}

	@Override
	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	}
	
	
}
