package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.UnitOfMeasureCommand;

public interface MeasureUnitService {
    Set<UnitOfMeasureCommand> findAll();
}