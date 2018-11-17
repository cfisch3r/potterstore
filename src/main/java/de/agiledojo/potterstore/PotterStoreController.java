package de.agiledojo.potterstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PotterStoreController {

    private PriceCalculation priceCalculation;

    @Autowired
    public PotterStoreController(PriceCalculation priceCalculation) {
        this.priceCalculation = priceCalculation;
    }

    @RequestMapping(method= RequestMethod.POST,path="/price")
    public Price price(@RequestBody List<JSONBookId> bookIds) {
        return priceCalculation.priceFor(new ArrayList<BookId>(bookIds));
    }

}
