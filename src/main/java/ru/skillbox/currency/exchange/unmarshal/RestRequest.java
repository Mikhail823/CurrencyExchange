package ru.skillbox.currency.exchange.unmarshal;

import liquibase.pro.packaged.C;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.ConvertToListMapper;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
import ru.skillbox.currency.exchange.xmlparser.CurrencyJaxb;
import ru.skillbox.currency.exchange.xmlparser.CurrencyLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableAsync
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class RestRequest {

    @Value("${crb.uri}")
    private String urlCrb;
    private final CurrencyRepository currencyRepository;
    private final ConvertToListMapper convert;


     public String requestCrb(){
         RestTemplate restTemplate = new RestTemplate();
         log.info("A request is sent to CRB");
         return restTemplate.getForObject(urlCrb, String.class);
     }

     public void parserAndSaveXml(String xml){
         try {
             JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyLoader.class);
             InputStream in = new StringBufferInputStream(xml);
             Reader reader = new InputStreamReader(in, "UTF-8");

             Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
             CurrencyLoader currencyLoader = (CurrencyLoader) unmarshal.unmarshal(reader);
             saveDataCurrencyFromDb(currencyLoader);
         } catch (JAXBException e) {
             log.error("Ошибка получения данных: {}", e.getMessage());
         } catch (UnsupportedEncodingException e){
             log.error("Ошибка кодирования: {}", e.getMessage());
         }
     }

     @Scheduled(fixedRateString = "${fixedRate}", initialDelayString = "${initialDelay}")
     public void requestDataCrb(){
         parserAndSaveXml(requestCrb());
     }


     public void saveDataCurrencyFromDb(CurrencyLoader currencyLoader){

         List<Currency> listDataXml = convert.convertToListCurrency(currencyLoader.getValute());
         Map<String, Currency> map = listDataXml.stream().collect(Collectors.toMap(Currency::getCharIso, currency -> currency));
         List<Currency> listDataDb = currencyRepository.findAllByCharIsoIn(map.keySet());

         if (listDataDb.isEmpty()){
             List<Currency> list = map.values().stream().sorted(Comparator.comparing(Currency::getName)).collect(Collectors.toList());
             currencyRepository.saveAll(list);
             log.info("Saving data to a database.");
         } else {
             listDataDb.parallelStream().forEach(c -> {
                 String key = c.getCharIso();
                 Currency currency = map.get(key);
                 c.setNominal(currency.getNominal());
                 c.setName(currency.getName());
                 c.setValue(currency.getValue());
                 c.setCharIso(currency.getCharIso());
                 c.setIsoNumCode(currency.getIsoNumCode());
                 currencyRepository.save(c);
             });
             log.info("Database UPDATE!!!");
         }
     }

}
