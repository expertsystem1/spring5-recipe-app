package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findIngredientsByRecipeId(Long recipeId, Long ingredientId);
    IngredientCommand saveCommand(IngredientCommand command);
    void deleteById(Long recipeId, Long ingredientId);
}