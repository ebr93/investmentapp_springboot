package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepoI extends JpaRepository<Stock, Integer> {
}
