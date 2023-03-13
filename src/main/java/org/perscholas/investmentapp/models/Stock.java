package org.perscholas.investmentapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "stocks")
@Slf4j
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @NonNull
    @Column(name = "stock_name", length = 45)
    String stockName;

    @NonNull
    @Column(name = "ticker", length = 10)
    String ticker;

    @NonNull
    @Column(name = "price")
    @DecimalMin(value = "0.01",
            message = "Value for stock must be greater than 0")
    @Digits(integer=10, fraction=2)
    BigDecimal price;

    @NonNull
    @Column(name = "description", length = 100)
    String description;

    @OneToMany(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "stocks_and_possessions",
            joinColumns = @JoinColumn(name = "stock_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "possession_id", referencedColumnName = "id"))
    @ToString.Exclude
    List<Possession> userStocks = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockName, stock.stockName) && Objects.equals(ticker, stock.ticker) && Objects.equals(description, stock.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockName, ticker, description);
    }

    public void addPossession(Possession possession) {
        userStocks.add(possession);
        log.debug("add user position executed!");
    }

    public void removePossession(Possession possession) {
        userStocks.remove(possession);
        log.debug("remove user position executed!");
    }
}