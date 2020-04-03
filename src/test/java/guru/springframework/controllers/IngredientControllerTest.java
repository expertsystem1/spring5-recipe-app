package guru.springframework.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.IngredientConfiguratinHelper;

public class IngredientControllerTest {
	
	@Mock
	IngredientService service;
	
    @Mock
    RecipeService recipeService;

    IngredientController controller;
    
    IngredientConfiguratinHelper conf;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        conf = new IngredientConfiguratinHelper();
        controller = new IngredientController(recipeService,service, conf);
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
        mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENTS, placeHolderMap)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/list"))
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
	   mockMvc.perform(get(conf.getDynamicView(conf.PATH_RECIPE_INGREDIENT_SHOW, placeHolderMap)))
	   .andExpect(status().isOk())
	   .andExpect(view().name(conf.BASE_PATH+conf.SHOW))
	   .andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM));
   }
    
}
