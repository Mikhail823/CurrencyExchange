package ru.skillbox.currency.exchange.entity;

import lombok.*;
import ru.skillbox.currency.exchange.xmlparser.CurrencyJaxb;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "create_sequence", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "value")
    private Double value;

    @Column(name = "iso_num_code")
    private Long isoNumCode;

    @Column(name = "char_iso")
    private String charIso;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id) && Objects.equals(name, currency.name) && Objects.equals(nominal, currency.nominal) && Objects.equals(value, currency.value) && Objects.equals(isoNumCode, currency.isoNumCode) && Objects.equals(charIso, currency.charIso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nominal, value, isoNumCode, charIso);
    }
}
