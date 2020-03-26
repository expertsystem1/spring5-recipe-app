package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.model.Category;
import guru.springframework.model.Recipe;

public interface CategoryRepository extends CrudRepository<Category, Long>{

}
