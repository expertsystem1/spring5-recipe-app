package guru.springframework;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import guru.springframework.model.Category;
import guru.springframework.model.Difficulty;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Note;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.MeasureUnitRepository;
import guru.springframework.repositories.NoteRepository;
import guru.springframework.repositories.RecipeRepository;

@SpringBootApplication
public class Spring5RecipeAppApplication {

	private RecipeRepository recipeRepository;
	private IngredientRepository ingredientsRepository;
	private CategoryRepository categoryRepository;
	private NoteRepository noteRepository;
	private MeasureUnitRepository unitRepository;



	public Spring5RecipeAppApplication(RecipeRepository recipeRepository, IngredientRepository ingredientsRepository,
			CategoryRepository categoryRepository, NoteRepository noteRepository,
			MeasureUnitRepository unitRepository) {
		this.recipeRepository = recipeRepository;
		this.ingredientsRepository = ingredientsRepository;
		this.categoryRepository = categoryRepository;
		this.noteRepository = noteRepository;
		this.unitRepository = unitRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Spring5RecipeAppApplication.class, args);
	}

	private void loadData() {
		Recipe recipe = new Recipe();
		recipe.setDescription("Guacamole");
		Set<Category> categories = loadCategories();
		Set<Ingredient> ingredients = loadIngredients();
		Note note = loadNote();
		recipe.setCategories(categories);
		recipe.setCookTime(10);
		recipe.setDifficulty(Difficulty.LOW);
		recipe.setIngredients(ingredients);
		recipe.setNote(note);
		recipe.setPretTime(15);
		recipe.setServings(4);
		recipe.setSource("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		recipe.getCategories().forEach(category -> {
			category.addRecipe(recipe); 
			categoryRepository.save(category);
		});
		recipe.getIngredients().forEach(ingredient -> {
			ingredient.setRecipe(recipe);
			ingredientsRepository.save(ingredient);
		});
		note.setRecipe(recipe);
		noteRepository.save(note);
		recipeRepository.save(recipe);
	}

	private Note loadNote() {
		Note note = new Note();
		note.setRecipeNote("It is fine with chilly, meat and fish dishes");
		return note;
	}

	private Set<Category> loadCategories(){
		Set<Category> categories = new HashSet<Category>();

		Category sauceCategory = new Category();
		sauceCategory.setDescription("Sauces");
		categories.add(sauceCategory);

		Category sideCategory = new Category();
		sideCategory.setDescription("Side dishes");
		categories.add(sideCategory);
		
		return categories;
	}

	private Set<Ingredient> loadIngredients(){
		Set<Ingredient> ingredients = new HashSet<Ingredient>();

		Ingredient avocado = new Ingredient();
		avocado.setAmount(new BigDecimal(2));
		avocado.setDescription("ripe avocado");
		avocado.setUnitOfMeausure(unitRepository.findByDescription("Unit").get());
		ingredients.add(avocado);

		Ingredient salt = new Ingredient();
		salt.setAmount(new BigDecimal(1/4));
		salt.setDescription("salt");
		salt.setUnitOfMeausure(unitRepository.findByDescription("Teaspoon").get());
		ingredients.add(salt);


		Ingredient lime = new Ingredient();
		lime.setAmount(new BigDecimal(1));
		lime.setDescription("lime");
		lime.setUnitOfMeausure(unitRepository.findByDescription("Tablespoon").get());
		ingredients.add(lime);

		Ingredient onion = new Ingredient();
		onion.setAmount(new BigDecimal(2));
		onion.setDescription("red onion");
		onion.setUnitOfMeausure(unitRepository.findByDescription("Tablespoon").get());
		ingredients.add(onion);

		Ingredient chili = new Ingredient();
		chili.setAmount(new BigDecimal(1));
		chili.setDescription("serrano chilly");
		chili.setUnitOfMeausure(unitRepository.findByDescription("Unit").get());
		ingredients.add(chili);

		Ingredient cilantro = new Ingredient();
		cilantro.setAmount(new BigDecimal(2));
		cilantro.setDescription("cilantro");
		cilantro.setUnitOfMeausure(unitRepository.findByDescription("Tablespoon").get());
		ingredients.add(cilantro);

		Ingredient pepper = new Ingredient();
		pepper.setAmount(new BigDecimal(1));
		pepper.setDescription("black pepper");
		pepper.setUnitOfMeausure(unitRepository.findByDescription("Dash").get());
		ingredients.add(pepper);

		Ingredient tomato = new Ingredient();
		tomato.setAmount(new BigDecimal(1/2));
		tomato.setDescription("ripe tomato");
		tomato.setUnitOfMeausure(unitRepository.findByDescription("Unit").get());
		ingredients.add(tomato);

		Ingredient jicama = new Ingredient();
		jicama.setAmount(new BigDecimal(2));
		jicama.setDescription("rasishes or jicama");
		jicama.setUnitOfMeausure(unitRepository.findByDescription("Unit").get());
		ingredients.add(jicama);

		Ingredient tortillias = new Ingredient();
		tortillias.setAmount(new BigDecimal(1));
		tortillias.setDescription("tortillias");
		tortillias.setUnitOfMeausure(unitRepository.findByDescription("Bag").get());
		ingredients.add(tortillias);

		return ingredients;
	}
}
