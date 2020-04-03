package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe getRecipeById(long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}