package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.model.Ingredient;

public interface IngredientService {
    IngredientCommand findIngredientsByRecipeId(Long recipeId, Long ingredientId);
    /*Set<Ingredient> findAll();
    Ingredient findById(long id);
    IngredientCommand saveCommand(IngredientCommand command);
    IngredientCommand findCommandById(Long id);
    void deleteById(Long id);*/
}