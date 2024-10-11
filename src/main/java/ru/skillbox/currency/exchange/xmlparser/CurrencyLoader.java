package ru.skillbox.currency.exchange.xmlparser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyLoader {

    @XmlElement(name = "Valute")
    private List<CurrencyJaxb> valute;
}
