package guru.springframework.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.model.MeasureUnit;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeasureUnitRepositoryIT {
   
	@Autowired
	MeasureUnitRepository repository;
	
	@Before
   public void setUp() throws Exception{
	   
   }
   
   @Test
   public void findByDescription() throws Exception{
	   Optional<MeasureUnit> measureOptional = repository.findByDescription("Teaspoon");
	   assertEquals("Teaspoon", measureOptional.get().getDescription());	   
   }
   
}
