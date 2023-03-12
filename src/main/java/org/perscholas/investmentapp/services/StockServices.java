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
import java.util.Optional;
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

    public Stock createOrUpdate(Stock stock) {
        Optional<Stock> stockOptional =
                stockRepoI.findByTicker(stock.getTicker());
        if (stockOptional.isPresent()) {
            log.warn("createOrUpdate(): stock with ticker " + stock.getTicker() +
                    " already exists, updating");
            Stock originalStock = stockOptional.get();
            originalStock.setStockName(stock.getStockName());
            originalStock.setPrice(stock.getPrice());
            originalStock.setDescription(stock.getDescription());

            return stockRepoI.save(originalStock);
        } else {
            log.warn("createOrUpdate(): stock with ticker " + stock.getTicker() +
                    " already exists, updating");
            return stockRepoI.save(stock);
        }
    }

    public Stock savePositionToStock(int stockId, int possessionId) throws Exception {
        if(stockRepoI.findById(stockId).isPresent() && possessionRepoI.findById(possessionId).isPresent()) {
            Stock stock = stockRepoI.findById(stockId).get();
            Possession possession = possessionRepoI.findById(possessionId).get();

            log.warn("savePositionToStock(): stock with ticker " + stock.getTicker() +
                    " updating new possession");

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
                .map((stock) -> new StockDTO(stock.getStockName(), stock.getTicker(), stock.getPrice(), stock.getDescription()))
                .collect(Collectors.toList());
    }
}
