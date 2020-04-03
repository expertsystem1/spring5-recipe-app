package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.model.MeasureUnit;
import lombok.Synchronized;

@Component
public class UoMCommnandToUoM implements Converter<UnitOfMeasureCommand, MeasureUnit>{
	
	@Synchronized
	@Nullable
	@Override
    public MeasureUnit convert(UnitOfMeasureCommand source) {
    	if (source == null) {
    		return null;
    	}
    	final MeasureUnit uom = new MeasureUnit();
    	uom.setDescription(source.getDescription());
    	uom.setId(source.getId());
    	return uom;
    }

}
