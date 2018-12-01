package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PotterParametersController {

    private ParameterRepository repository;

    @Autowired
    public PotterParametersController(ParameterRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path="/parameters/{id}",method = RequestMethod.PUT)
    public void parameters(@PathVariable("id") String parameterId, @RequestBody JSONBookPrice price) {
        repository.saveOrUpdateSingleBookPrice(price);
    }
}
