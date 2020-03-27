package guru.springframework.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	
	Category category;
	
	@Before
	public void setUp() {
		category = new Category();
	}
	
	@Test
	public void getId() throws Exception {
		Long idValue = 4l;
		category.setId(idValue);
		assertEquals(idValue, category.getId());
	}
	
	@Test
	public void getDescription() throws Exception {
		String description = "DESCRIPTION";
		category.setDescription(description);
		assertEquals(description, category.getDescription());
	}
	
	@Test
	public void getRecipe() throws Exception {
		Set<Recipe> recipes = new HashSet<Recipe>();
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		Recipe recipe2 = new Recipe();
		recipe1.setId(2L);
		recipes.add(recipe1);
		recipes.add(recipe2);
		category.setRecipes(recipes);
        assertEquals(recipes.size(), category.getRecipes().size());			
	}
	
}
