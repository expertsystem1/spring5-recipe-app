package guru.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.helper.ImageConfigurationHelper;


public class ImageControllerTest {
	 @Mock
	    ImageService imageService;

	    @Mock
	    RecipeService recipeService;
	    
	    Map<String,String> params;

	    ImageController controller;

	    ImageConfigurationHelper conf;

	    MockMvc mockMvc;

	    @Before
	    public void setUp() throws Exception {
	        MockitoAnnotations.initMocks(this);
	        conf = new ImageConfigurationHelper();
	        controller = new ImageController(imageService, recipeService,conf);
	        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	        params = new HashMap<String, String>();
	    }

	    @Test
	    public void getImageForm() throws Exception {
	        //given
	        RecipeCommand command = new RecipeCommand();
	        command.setId(1L);

	        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

	        //when
	        params.put(conf.RECIPE_ID, Long.toString(1L));
	        mockMvc.perform(get(conf.getDynamicView(conf.PATH_UPLOAD_FORM, params, false)))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists(conf.MODEL_ATTRIBUTE_SINGLE_RECIPE));

	        verify(recipeService, times(1)).findCommandById(anyLong());

	    }

	    @Test
	    public void handleImagePost() throws Exception {
	        MockMultipartFile multipartFile =
	                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
	                        "Spring Framework Guru".getBytes());
	        params.put(conf.RECIPE_ID, Long.toString(1L));
	        mockMvc.perform(multipart(conf.getDynamicView(conf.PATH_UPLOAD_FORM, params, false)).file(multipartFile))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(header().string("Location", conf.getDynamicView(conf.PATH_RECIPE_SHOW, params, false)));

	        verify(imageService, times(1)).saveImageFile(anyLong(), Mockito.any());
	    }
	    
	    @Test
	    public void renderImageFromDB() throws Exception{
	    	RecipeCommand command = new RecipeCommand();
	    	command.setId(1L);
	    	String s = "fake image text";
	    	Byte[] bytes = new Byte[s.getBytes().length];
	    	int i = 0;
	    	for (byte b : s.getBytes()) {
	    		bytes[i++] = b;
	    	}
	    	
	    	command.setImage(bytes);
	    	 params.put(conf.RECIPE_ID, Long.toString(1L));
	    	
	    	Mockito.when(imageService.getRecipeImage(anyLong())).thenReturn(bytes);
	    	MockHttpServletResponse response = mockMvc.perform(get(conf.getDynamicView(conf.PATH_IMAGE_RENDER, params, false)))
	    									   .andExpect(status().isOk())
	    									   .andReturn().getResponse();
	    	byte[] responseBytes = response.getContentAsByteArray();
	    	assertEquals(s.getBytes().length, responseBytes.length);
	    }

}
