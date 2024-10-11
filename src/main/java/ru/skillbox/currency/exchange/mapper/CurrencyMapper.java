package ru.skillbox.currency.exchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyShortDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.xmlparser.CurrencyJaxb;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    Currency convertToEntity(CurrencyDto currencyDto);

    @Mapping(target = "name", expression = "java(String.format(\"%s\", currency.getName()))")
    @Mapping(target = "value", source = "value")
    CurrencyShortDto convertToCurrencyShortDto(Currency currency);
}
