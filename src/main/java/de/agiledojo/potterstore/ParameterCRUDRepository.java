package de.agiledojo.potterstore;

import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Parameter;

public interface ParameterCRUDRepository extends CrudRepository<ParameterRecord,Long> {
}
