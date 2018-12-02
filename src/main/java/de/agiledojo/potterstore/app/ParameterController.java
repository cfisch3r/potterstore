package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.Price;
import de.agiledojo.potterstore.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;

@RestController
public class ParameterController {

    private ParameterRepository repository;

    @Autowired
    public ParameterController(ParameterRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path="/parameters/{id}",method = RequestMethod.PUT)
    public void parameters(@PathVariable("id") String parameterId, @RequestBody JSONPrice price) {
        var singleBookPrice = new Price() {

            @Override
            public BigDecimal getAmount() {
                return price.getAmount();
            }

            @Override
            public Currency getCurrency() {
                return Currency.getInstance(price.getCurrency());
            }
        };
        repository.saveOrUpdateSingleBookPrice(singleBookPrice);
    }
}
