package guru.springframework.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private BigDecimal amount;

	@OneToOne (fetch = FetchType.EAGER)
	private MeasureUnit unitOfMeausure;

	@ManyToOne
	private Recipe recipe;
	
	public Ingredient() {}
	
	public Ingredient(String description, BigDecimal amount, MeasureUnit uom) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeausure = uom;
	}

	public Ingredient(String description, BigDecimal amount, MeasureUnit uom, Recipe recipe) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeausure = uom;
		this.recipe = recipe;
	}

	
}
