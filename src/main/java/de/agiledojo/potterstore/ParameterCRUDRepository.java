package de.agiledojo.potterstore;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParameterCRUDRepository extends CrudRepository<ParameterRecord,Long> {

    List<ParameterRecord> findByParamKey(String paramKey);
}
