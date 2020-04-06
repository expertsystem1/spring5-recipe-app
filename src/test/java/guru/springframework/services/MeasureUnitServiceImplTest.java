package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UoMToUoMCommand;
import guru.springframework.model.MeasureUnit;
import guru.springframework.repositories.MeasureUnitRepository;
import guru.springframework.services.impl.MeasureUniteServiceImpl;

public class MeasureUnitServiceImplTest {
	
     UoMToUoMCommand toCommandConverter = new UoMToUoMCommand();
     MeasureUnitService service;
     
     @Mock
     MeasureUnitRepository repository;
     
     @Before
     public void setUp() {
    	 MockitoAnnotations.initMocks(this);
    	 service = new MeasureUniteServiceImpl(repository, toCommandConverter);
     }
     
     @Test
     public void findAll() {
    	 Set<MeasureUnit> unitOfMeasures = new HashSet<MeasureUnit>();
    	 MeasureUnit u1 = new MeasureUnit();
    	 u1.setId(1L);
    	 unitOfMeasures.add(u1);
    	 
    	 MeasureUnit u2 = new MeasureUnit();
    	 u2.setId(2L);
    	 unitOfMeasures.add(u2);
    	 
    	 when(repository.findAll()).thenReturn(unitOfMeasures);
    	 
    	 Set<UnitOfMeasureCommand> commands = service.findAll();
    	 assertEquals(commands.size(), unitOfMeasures.size());
    	 verify(repository, times(1)).findAll();
     }
}
