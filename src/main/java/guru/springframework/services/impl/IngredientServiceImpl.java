package guru.springframework.services.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
	
	private final IngredientRepository repository;
    private final IngredientToIngredientCommand toCommandConverter;
    private final IngredientCommandToIngredient fromCommandConverter;
    
    public IngredientServiceImpl(IngredientRepository repository, IngredientToIngredientCommand toCommandConverter,
			IngredientCommandToIngredient fromCommandConverter) {
		this.repository = repository;
		this.toCommandConverter = toCommandConverter;
		this.fromCommandConverter = fromCommandConverter;
	}

	@Override
	public Set<Ingredient> findAll() {
        Set<Ingredient> items = new HashSet<Ingredient>();
        repository.findAll().iterator().forEachRemaining(items::add);
        return items;
	}

	@Override
	public Ingredient findById(long id) {
	    Optional<Ingredient> optionaItem = repository.findById(id);
	    if(!optionaItem.isPresent()) {
	    	throw new RuntimeException("Ingredient Not Found!!!");
	    }
	    return optionaItem.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveCommand(IngredientCommand command) {
		Ingredient detachItem = fromCommandConverter.convert(command);
		Ingredient savedItem = repository.save(detachItem);
		log.debug("Saved Ingredient Id: "+savedItem.getId());
		return toCommandConverter.convert(savedItem);
	}

	@Override
	@Transactional
	public IngredientCommand findCommandById(Long id) {
		Optional<Ingredient> optionalItem = repository.findById(id);
		if (!optionalItem.isPresent()) {
			throw new RuntimeException("Ingredient not found!!!");
		}
		return toCommandConverter.convert(optionalItem.get());
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
