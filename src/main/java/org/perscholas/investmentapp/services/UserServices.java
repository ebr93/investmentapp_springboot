package org.perscholas.investmentapp.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.models.Address;
import org.perscholas.investmentapp.models.Possession;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserServices {
    UserRepoI userRepoI;
    PossessionRepoI possessionRepoI;
    AddressRepoI addressRepoI;

    @Autowired
    public UserServices(UserRepoI userRepoI, PossessionRepoI possessionRepoI) {
        this.userRepoI = userRepoI;
        this.possessionRepoI = possessionRepoI;
    }

    public List<Possession> savePositionToUser(String email, int possessionId) throws Exception {
        if(userRepoI.findByEmailAllIgnoreCase(email).isPresent() && possessionRepoI.findById(possessionId).isPresent()) {
            User user = userRepoI.findByEmailAllIgnoreCase(email).get();
            Possession possession = possessionRepoI.findById(possessionId).get();
            user.addPossession(possession);

            user = userRepoI.saveAndFlush(user);

            return user.getUserPossessions();
        } else {
            throw new Exception("saving a possession to the user " + email + " did not go well!!!!!");
        }
    }

    public User savePositionToUser(Possession possession) throws Exception {
        if(userRepoI.findByEmailAllIgnoreCase(possession.getUser().getEmail()).isPresent()) {
            User user = userRepoI.findByEmailAllIgnoreCase(possession.getUser().getEmail()).get();
            user.addPossession(possession);

            user = userRepoI.saveAndFlush(user);

            return user;
        } else {
            throw new Exception("saving a possession to the user " + possession.getUser().getEmail() + " did not go well!!!!!");
        }
    }
    
    public User saveAddressToUser(String email, int addressId) throws Exception {
        if(userRepoI.findByEmailAllIgnoreCase(email).isPresent() && addressRepoI.findById(addressId).isPresent()) {
            User user = userRepoI.findByEmailAllIgnoreCase(email).get();
            Address address = addressRepoI.findById(addressId).get();
            user.setAddress(address);

            user = userRepoI.saveAndFlush(user);

            return user;
        } else {
            throw new Exception("saving an address to the user " + email + " did not go well!!!!!");
        }
    }

    public List<Possession> retrievePortfolio(String email) throws Exception {
        if (userRepoI.findByEmailAllIgnoreCase(email).isPresent()) {
            return userRepoI.findByEmailAllIgnoreCase(email).get().getUserPossessions();
        } else {
            throw new Exception("retrieving stock portfolio of the user " + email + " did not go well!!!!!");
        }
    }
}
