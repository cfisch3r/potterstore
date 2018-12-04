package de.agiledojo.potterstore.app.parameter;

import de.agiledojo.potterstore.ParameterRepository;
import de.agiledojo.potterstore.PriceCalculation;
import de.agiledojo.potterstore.app.JSONPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
public class ParameterController {

    private ParameterRepository repository;

    @Autowired
    public ParameterController(ParameterRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path="/parameters/price",method = RequestMethod.PUT)
    public void parameters(@RequestBody JSONPrice price) {
        var singleBookPrice = PriceCalculation.price(price.getAmount(),Currency.getInstance(price.getCurrency()));
        repository.saveOrUpdateSingleBookPrice(singleBookPrice);
    }
}