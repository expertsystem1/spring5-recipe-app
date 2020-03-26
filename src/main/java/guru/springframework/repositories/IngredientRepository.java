package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

}
