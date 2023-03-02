package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepoI extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByTicker(String ticker);
}
