package guru.springframework.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.MeasureUnitService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.IngredientConfiguratinHelper;

public class IngredientControllerTest {

	@Mock
	IngredientService service;

	@Mock
	RecipeService recipeService;

	@Mock
	MeasureUnitService uomService;

	IngredientController controller;

	IngredientConfiguratinHelper conf;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		conf = new IngredientConfiguratinHelper();
		controller = new IngredientController(recipeService,service,uomService, conf);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		//when
		Map<String,String> placeHolderMap = new HashMap<String,String>();
		placeHolderMap.put(conf.RECIPE_ID,"1");
		mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENTS, placeHolderMap,false)))
		.andExpect(status().isOk())
		.andExpect(view().name(conf.BASE_PATH+conf.LIST))
		.andExpect(model().attributeExists("recipe"));
		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void testShowIngredient() throws Exception{
		IngredientCommand command = new IngredientCommand();
		when(service.findIngredientsByRecipeId(anyLong(),anyLong())).thenReturn(command);
		Map<String,String> placeHolderMap = new HashMap<String,String>();
		placeHolderMap.put(conf.RECIPE_ID,"1");
		placeHolderMap.put(conf.INGREDIENT_ID,"2");
		mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_SHOW, placeHolderMap,false)))
		.andExpect(status().isOk())
		.andExpect(view().name(conf.BASE_PATH+conf.SHOW))
		.andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM));
	}

	@Test
	public void testUpdateIngredientForm() throws Exception{
		IngredientCommand command = new IngredientCommand();
		when(service.findIngredientsByRecipeId(anyLong(), anyLong())).thenReturn(command);
		when(uomService.findAll()).thenReturn(new HashSet<>());
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put(conf.RECIPE_ID, Long.toString(1L));
		parameters.put(conf.INGREDIENT_ID, Long.toString(2L));
		mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_UPDATE, parameters, false)))
		.andExpect(status().isOk())
		.andExpect(view().name(conf.BASE_PATH+conf.FORM_NAME))
		.andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM))
		.andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_ITEM_UOM_LIST));
	}

	@Test
	public void testSaveOrUpdate() throws Exception{
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		when(service.saveCommand(Mockito.any())).thenReturn(command);

		Map<String,String> postParams = new HashMap<String, String>();
		postParams.put(conf.RECIPE_ID, Long.toString(2L));

		Map<String,String> redirectParams = new HashMap<String, String>();
		redirectParams.put(conf.RECIPE_ID, Long.toString(2L));
		redirectParams.put(conf.INGREDIENT_ID, Long.toString(3L));

		String postUrl = conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_POST, postParams, false);
		mockMvc.perform(post(postUrl)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_SHOW, redirectParams, true)));	
	}

	@Test
	public void testNewIngredientForm() throws Exception{
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(uomService.findAll()).thenReturn(new HashSet<>());

		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put(conf.RECIPE_ID, Long.toString(1L));

		mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENTS_NEW, parameters, false)))
		.andExpect(status().isOk())
		.andExpect(view().name(conf.BASE_PATH+conf.FORM_NAME))
		.andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM))
		.andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_ITEM_UOM_LIST));
	}

	@Test
	public void testDeleteIngredient() throws Exception {

		//then
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put(conf.RECIPE_ID, Long.toString(2L));
		parameters.put(conf.INGREDIENT_ID, Long.toString(3L));

		Map<String,String> redirectParams = new HashMap<String, String>();
		redirectParams.put(conf.RECIPE_ID, Long.toString(2L));

		mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_DELETE, parameters, false))
				)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENTS, redirectParams, true)));

		verify(service, times(1)).deleteById(anyLong(), anyLong());

	}

}
