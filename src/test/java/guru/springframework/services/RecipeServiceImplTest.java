package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.impl.RecipeServiceImpl;

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
	    when(recipeService.findAll()).thenReturn(recipesData);
		Set<Recipe> recipes = recipeService.findAll();
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
	
    
    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception{
    	Optional<Recipe> recipeOptional = Optional.empty();
    	when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
    	Recipe recipeReturned = recipeService.findById(1L);
    }

}
