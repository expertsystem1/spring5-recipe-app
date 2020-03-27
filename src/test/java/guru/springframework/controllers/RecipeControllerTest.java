package guru.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import guru.springframework.model.Recipe;
import guru.springframework.services.RecipeService;

public class RecipeControllerTest {
	
	private final String RECIPE_LIST = "recipes/index";
	
	RecipeController controller;
	
	@Mock
	Model model;
	
	@Mock
	RecipeService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(service);
	}

	@Test
	public void getRecipeList() {
		String viewName = controller.getRecipeList(model);
		assertEquals(viewName, RECIPE_LIST);
		verify(service,times(1)).getRecipes();
		verify(model, times(1)).addAttribute("recipes", new HashSet<Recipe>());
	}

}
