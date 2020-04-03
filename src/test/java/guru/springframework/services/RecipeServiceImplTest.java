package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
	}

	@Test
	public void getRecipes() {
		Recipe recipe = new Recipe();
	    Set<Recipe> recipesData = new HashSet<Recipe>();
	    recipesData.add(recipe);
	    when(recipeService.getRecipes()).thenReturn(recipesData);
		Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(recipes.size(), 1);
		//verify that find all is called 1
		verify(recipeRepository,times(1)).findAll();
	}
	
	@Test
	public void testDeleteById() throws Exception {
		Long id = Long.valueOf(2L);
		recipeService.deleteById(id);
		verify(recipeRepository, times(1)).deleteById(Mockito.anyLong());
	}

}
