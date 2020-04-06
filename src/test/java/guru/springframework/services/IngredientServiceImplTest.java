package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UoMCommnandToUoM;
import guru.springframework.converters.UoMToUoMCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.MeasureUnitRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.impl.IngredientServiceImpl;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    
    @Mock
    MeasureUnitRepository uomRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UoMToUoMCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UoMCommnandToUoM());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository,ingredientToIngredientCommand,ingredientCommandToIngredient, uomRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        
        
        //then
        IngredientCommand ingredientCommand = ingredientService.findIngredientsByRecipeId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
    
    @Test
    public void testSaveIngredient() {
    	IngredientCommand command = new IngredientCommand();
    	command.setId(3L);
    	command.setRecipeId(2L);
    	
    	Optional<Recipe> recipeOptional = Optional.of(new Recipe());
    	Recipe savedRecipe = new Recipe();
    	savedRecipe.addIngredient(new Ingredient());
    	savedRecipe.getIngredients().iterator().next().setId(3L);
    	
    	when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
    	when(recipeRepository.save(Mockito.any())).thenReturn(savedRecipe);
    	
    	IngredientCommand savedCommand = ingredientService.saveCommand(command);
    	assertEquals(Long.valueOf(3L), savedCommand.getId());
    	verify(recipeRepository,times(1)).findById(anyLong());
    	verify(recipeRepository,times(1)).save(Mockito.any());
    	
    	
    }

}