package guru.springframework.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.model.Category;
import guru.springframework.model.MeasureUnit;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.MeasureUnitRepository;

@Controller
public class IndexController {
	
	private CategoryRepository categoryRepository;
	private MeasureUnitRepository measureRepository;


	public IndexController(CategoryRepository categoryRepository, MeasureUnitRepository measureRepository) {
		this.categoryRepository = categoryRepository;
		this.measureRepository = measureRepository;
	}



	@RequestMapping({"","/","index","index.html"})
	public String getIndex() {
		Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
		Optional<MeasureUnit> maeasurOptional = measureRepository.findByDescription("Tablespoon");
		System.out.println("Category ID is: "+categoryOptional.get().getId());
		System.out.println("Unit of Measure ID is: "+maeasurOptional.get().getId());
		return "index";
	}

}
