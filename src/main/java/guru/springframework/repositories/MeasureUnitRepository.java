package guru.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.model.MeasureUnit;

public interface MeasureUnitRepository extends CrudRepository<MeasureUnit, Long>{
	Optional<MeasureUnit> findByDescription(String description);
}
