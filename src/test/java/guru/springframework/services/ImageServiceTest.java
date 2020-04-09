package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.impl.ImageServiceImpl;

public class ImageServiceTest {
	
	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;
	
	@Before
	public void setUp() {
		 MockitoAnnotations.initMocks(this);
		 imageService = new ImageServiceImpl(recipeRepository);
	}
	
	@Test
	public void saveImageFile() {
		Long id = 1L;
		MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt","text/plain","mytest".getBytes());
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
		ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);
		imageService.saveImageFile(id, multipartFile);
		verify(recipeRepository,Mockito.times(1)).save(captor.capture());
		Recipe savedRecipe = captor.getValue();
		assertEquals(id, savedRecipe.getId());
	}

}
