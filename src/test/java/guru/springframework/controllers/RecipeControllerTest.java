package guru.springframework.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.IngredientConfiguratinHelper;
import guru.springframework.services.helper.RecipeConfigurationHelper;


public class RecipeControllerTest {

    @Mock
    RecipeService service;

    RecipeController controller;
    RecipeConfigurationHelper conf;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        conf = new RecipeConfigurationHelper();
        controller = new RecipeController(service,conf);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetItem() throws Exception {

        Recipe item = new Recipe();
        item.setId(1L);

        when(service.findById(anyLong())).thenReturn(item);

        mockMvc.perform(get("/"+conf.BASE_PATH+"1/"+conf.SHOW))
                .andExpect(status().isOk())
                .andExpect(view().name(conf.BASE_PATH+conf.SHOW))
                .andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM));
    }

    @Test
    public void testGetNewItemForm() throws Exception {
        mockMvc.perform(get("/"+conf.BASE_PATH+conf.NEW))
                .andExpect(status().isOk())
                .andExpect(view().name(conf.BASE_PATH+conf.FORM_NAME))
                .andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM));
    }

    @Test
    public void testPostNewItemForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(service.saveCommand(any())).thenReturn(command);

        mockMvc.perform(post("/"+conf.BASE_PATH)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"+conf.BASE_PATH+"2/"+conf.SHOW+"/"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
    	RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(service.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/"+conf.BASE_PATH+"1/"+conf.UPDATE))
                .andExpect(status().isOk())
                .andExpect(view().name(conf.BASE_PATH+conf.FORM_NAME))
                .andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_ITEM));
    }
    
    @Test
    public void testDeleteAction() throws Exception {
         mockMvc.perform(get("/"+conf.BASE_PATH+"1/"+conf.DELETE))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"+conf.BASE_PATH));
      
         verify(service,times(1)).deleteById(Mockito.anyLong());
    }
}