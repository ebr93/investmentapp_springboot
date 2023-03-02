package org.perscholas.investmentapp.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.Possession;
import org.perscholas.investmentapp.models.Stock;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class StockServices {
    StockRepoI stockRepoI;
    PossessionRepoI possessionRepoI;

    public StockServices(StockRepoI stockRepoI, PossessionRepoI possessionRepoI) {
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
    }

    public Stock savePositionToStock(int stockId, int possessionId) throws Exception {
        if(stockRepoI.findById(stockId).isPresent() && possessionRepoI.findById(possessionId).isPresent()) {
            Stock stock = stockRepoI.findById(stockId).get();
            Possession possession = possessionRepoI.findById(possessionId).get();
            stock.addPossession(possession);

            stock = stockRepoI.saveAndFlush(stock);

            return stock;
        } else {
            throw new Exception("saving a possession to the stock with ID " + stockId + " did not go well!!!!!");
        }
    }

    public List<StockDTO> allStocks() {
        return stockRepoI.findAll()
                .stream()
                .map((stock) -> new StockDTO(stock.getInvestmentName(), stock.getTicker(), stock.getPrice(), stock.getDescription()))
                .collect(Collectors.toList());
    }
}
