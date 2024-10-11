package ru.skillbox.currency.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.currency.exchange.entity.Currency;

import java.util.List;
import java.util.Set;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByIsoNumCode(Long isoNumCode);

    @Modifying
    @Query(value = "UPDATE Currency AS c SET c = :currency WHERE c.id = :id")
    void updateCurrency(@Param("currency") Currency post, @Param("id") String id);

    List<Currency> findAllByCharIsoIn(Set<String> strings);
}
