package org.perscholas.investmentapp.services;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.UserPositionRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.perscholas.investmentapp.models.UserPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserAndPositionServices {
    UserRepoI userRepoI;
    UserPositionRepoI userPositionRepoI;
    StockRepoI stockRepoI;

    @Autowired
    public UserAndPositionServices(UserRepoI userRepoI, UserPositionRepoI userPositionRepoI, StockRepoI stockRepoI) {
        this.userRepoI = userRepoI;
        this.userPositionRepoI = userPositionRepoI;
        this.stockRepoI = stockRepoI;

    }

    //@Transactional(rollbackOn = Exception.class)
    public void addingNewPosition(UserPosition us) {
        userPositionRepoI.saveAndFlush(us);

        // ADD Optional check and exception
        User currentUser =  userRepoI.findById(us.getUser().getId()).get();
        Stock currentStock = stockRepoI.findById(us.getInvestment().getId()).get();
        currentUser.getUserStocks().add(us);
        currentStock.getUserStocks().add(us);

        userRepoI.saveAndFlush(currentUser);
        stockRepoI.saveAndFlush(currentStock);
    }

    public List<StockDTO> allStocks() {
        return stockRepoI.findAll()
                .stream()
                .map((stock) -> new StockDTO(stock.getInvestmentName(), stock.getTicker(), stock.getPrice(), stock.getDescription()))
                .collect(Collectors.toList());
    }

    //@Transactional(rollbackOn = UserPosition.class)
    public List<UserPosition> allUserPositions(int userID) {
        return userPositionRepoI.findByUser(userID);
    }

}
