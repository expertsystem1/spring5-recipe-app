package guru.springframework.services.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UoMToUoMCommand;
import guru.springframework.repositories.MeasureUnitRepository;
import guru.springframework.services.MeasureUnitService;


@Service
public class MeasureUniteServiceImpl implements MeasureUnitService {
	
	private final MeasureUnitRepository repository;
	private final UoMToUoMCommand converter;
	
	

	public MeasureUniteServiceImpl(MeasureUnitRepository repository, UoMToUoMCommand converter) {
		this.repository = repository;
		this.converter = converter;
	}



	@Override
	public Set<UnitOfMeasureCommand> findAll() {
		
        return StreamSupport.stream(repository.findAll()
                .spliterator(), false)
                .map(converter::convert)
                .collect(Collectors.toSet());
	}
	
}
