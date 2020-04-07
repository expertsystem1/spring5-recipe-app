package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findIngredientsByRecipeId(Long recipeId, Long ingredientId);
    IngredientCommand saveCommand(IngredientCommand command);
    /*Set<Ingredient> findAll();
    Ingredient findById(long id);
    IngredientCommand saveCommand(IngredientCommand command);
    IngredientCommand findCommandById(Long id);
    void deleteById(Long id);*/
}