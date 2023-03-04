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

    public Possession createOrUpdate(Possession possession) {
        Optional<Possession> possessionOptional = possessionRepoI.findByUserAndStock(possession.getUser(), possession.getStock());
        if (possessionOptional.isPresent()) {
            log.debug("Possession with user " + possession.getUser().getEmail() + " already exists");
            Possession originalPosition = possessionOptional.get();
            originalPosition.setShares(possession.getShares());

            originalPosition = possessionRepoI.save(originalPosition);

            return originalPosition;
        } else {
            log.debug("Possession with user " + possession.getUser().getEmail() + " does not exists");

            return possessionRepoI.save(possession);
        }
    }

    public Possession createOrUpdate(Possession possession, User user, Stock stock) {
        Optional<Possession> possessionOptional = possessionRepoI.findByUserAndStock(possession.getUser(), possession.getStock());
        if (possessionOptional.isPresent()) {
            log.debug("Possession with user " + possession.getUser().getEmail() + " already exists");
            Possession originalPosition = possessionOptional.get();
            originalPosition.setShares(possession.getShares());

            originalPosition = possessionRepoI.save(originalPosition);

            return originalPosition;
        } else {
            log.debug("Possession with user " + possession.getUser().getEmail() + " does not exists");
            Possession newPossession = possessionRepoI.save(possession);

            user.addPossession(newPossession);
            stock.addPossession(newPossession);
            userRepoI.saveAndFlush(user);
            stockRepoI.saveAndFlush(stock);
            return newPossession;
        }
    }

}
