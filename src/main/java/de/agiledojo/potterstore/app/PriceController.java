package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.BookId;
import de.agiledojo.potterstore.Price;
import de.agiledojo.potterstore.PriceCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    private PriceCalculation priceCalculation;

    @Autowired
    public PriceController(PriceCalculation priceCalculation) {
        this.priceCalculation = priceCalculation;
    }

    @RequestMapping(method= RequestMethod.POST,path="/price")
    public JSONPrice price(@RequestBody List<JSONBookId> bookIds) {
        Price price = priceCalculation.priceFor(new ArrayList<BookId>(bookIds));
        return new JSONPrice(price);
    }

}
