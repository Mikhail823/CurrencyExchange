package ru.skillbox.currency.exchange.mapper;

import org.springframework.stereotype.Component;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.xmlparser.CurrencyJaxb;

import java.util.ArrayList;
import java.util.List;
@Component
public class ConvertToListMapper {

    public List<Currency> convertToListCurrency(List<CurrencyJaxb> list) {
        if ( list == null ) {
            return null;
        }

        List<Currency> list1 = new ArrayList<Currency>( list.size() );
        for ( CurrencyJaxb currencyJaxb : list ) {
            list1.add( currencyJaxbToCurrency( currencyJaxb ) );
        }

        return list1;
    }

    protected Currency currencyJaxbToCurrency(CurrencyJaxb currencyJaxb) {
        if ( currencyJaxb == null ) {
            return null;
        }

        Currency currency = new Currency();

        currency.setName( currencyJaxb.getName() );
        currency.setNominal( currencyJaxb.getNominal() );
        currency.setCharIso(currencyJaxb.getCharCode());
        currency.setIsoNumCode(currencyJaxb.getNumCode());

        if ( currencyJaxb.getValue() != null ) {
            currency.setValue( Double.parseDouble( currencyJaxb.getValue().replace(",", ".") ) );
        }

        return currency;
    }
}
