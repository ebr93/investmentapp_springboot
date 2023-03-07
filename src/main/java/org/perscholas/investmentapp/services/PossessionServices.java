package org.perscholas.investmentapp.services;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.models.Possession;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = Exception.class)
public class PossessionServices {
    UserRepoI userRepoI;
    PossessionRepoI possessionRepoI;
    StockRepoI stockRepoI;
    UserServices userServices;
    StockServices stockServices;

    @Autowired
    public PossessionServices(UserRepoI userRepoI, PossessionRepoI possessionRepoI,
                              StockRepoI stockRepoI, UserServices userServices,
                              StockServices stockServices) {
        this.userRepoI = userRepoI;
        this.possessionRepoI = possessionRepoI;
        this.stockRepoI = stockRepoI;
        this.userServices = userServices;
        this.stockServices = stockServices;
    }

    public Possession createOrUpdate(Possession possession) throws Exception {
        Optional<Possession> possessionOptional = possessionRepoI.findByUserAndStock(possession.getUser(), possession.getStock());
        Optional<User> userOptional = userRepoI.findByEmail(possession.getUser().getEmail());
        Optional<Stock> stockOptional = stockRepoI.findByTicker(possession.getStock().getTicker());


        if (possessionOptional.isPresent()) {
            log.debug("Possession with user " + possession.getUser().getEmail() + " already exists");
            Possession originalPosition = possessionOptional.get();
            originalPosition.setShares(possession.getShares());

            originalPosition = possessionRepoI.save(originalPosition);

            return originalPosition;
        } else if (userOptional.isPresent() && stockOptional.isPresent()){
            log.debug("Possession with user " + possession.getUser().getEmail() + " does not exists");
            User confirmedUser = userOptional.get();
            Stock confirmedStock = stockOptional.get();

            possessionRepoI.saveAndFlush(possession);

            confirmedUser.addPossession(possession);
            confirmedStock.addPossession(possession);
            userRepoI.save(confirmedUser);
            stockRepoI.save(confirmedStock);

            return possessionRepoI.save(possession);
        } else {
            throw new Exception("createOrUpdate(): saving a possession " + possession + " did not go well!");
        }
    }

    public Possession createOrUpdate(Possession possession, User user, Stock stock) {
        Optional<Possession> possessionOptional = possessionRepoI.findByUserAndStock(possession.getUser(), possession.getStock());

        if (possessionOptional.isPresent()) {
            log.debug("createOrUpdate(): Possession with user " + possession.getUser().getEmail() + " already exists");

            Possession originalPosition = possessionOptional.get();
            originalPosition.setShares(possession.getShares());

            originalPosition = possessionRepoI.save(originalPosition);

            return originalPosition;
        } else {
            log.debug("createOrUpdate(): New possession for user " + possession.getUser().getEmail() + " has been created");
            Possession newPossession = possessionRepoI.saveAndFlush(possession);
            log.debug("createOrUpdate(): New possession info is " + newPossession);

            user.addPossession(newPossession);
            stock.addPossession(newPossession);
            userRepoI.save(user);
            stockRepoI.save(stock);
            return newPossession;
        }
    }

}
