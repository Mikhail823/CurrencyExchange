package ru.skillbox.currency.exchange.xmlparser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyJaxb {

    @XmlElement(name = "NumCode")
    private Long numCode;
    @XmlElement(name = "CharCode")
    private String charCode;
    @XmlElement(name = "Nominal")
    private Long nominal;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Value")
    private String value;
    @XmlElement(name = "VunitRate")
    private String vunitRate;
}
