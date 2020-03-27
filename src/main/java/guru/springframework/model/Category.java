package guru.springframework.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = {"recipes"})
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<Recipe>();
	
	public void addRecipe(Recipe recipe) {
	   if(this.recipes == null) {	
		   this.recipes = new HashSet<Recipe>();
	   }
	   this.recipes.add(recipe);
	}
    
    
}
