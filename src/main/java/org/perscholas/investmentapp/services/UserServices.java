package org.perscholas.investmentapp.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.AuthGroupRepoI;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserServices {
    private final AuthGroupRepoI authGroupRepoI;
    UserRepoI userRepoI;
    PossessionRepoI possessionRepoI;
    AddressRepoI addressRepoI;

    @Autowired
    public UserServices(UserRepoI userRepoI, PossessionRepoI possessionRepoI,
                        AuthGroupRepoI authGroupRepoI, AddressRepoI addressRepoI) {
        this.userRepoI = userRepoI;
        this.possessionRepoI = possessionRepoI;
        this.authGroupRepoI = authGroupRepoI;
        this.addressRepoI = addressRepoI;
    }

    public User createOrUpdate(User user) {
        Optional<User> userOptional = userRepoI.findByEmailAllIgnoreCase(user.getEmail());

        if (userOptional.isPresent()) {
            log.debug("UserServices: user with email " + user.getEmail() + " already exists");
            User originalUser = userOptional.get();
            originalUser.setFirstName(user.getFirstName());
            originalUser.setLastName(user.getLastName());
            // will need a method of its own, so does changing or adding address
            // originalUser.setPassword(user.getPassword());

            return userRepoI.save(originalUser);
        } else {
            log.debug("UserServices: user with email " + user.getEmail() + " has been created");
            user.setPassword(user.getPassword());
            AuthGroup newAuth = new AuthGroup(user.getEmail(), "ROLE_USER");
            authGroupRepoI.save(newAuth);

            return userRepoI.save(user);
        }
    }

    public User addOrUpdateAddress(Address address, User user) {
        Optional<Address> addressOptional = Optional.ofNullable(user.getAddress());
        if (addressOptional.isPresent()) {
            Address originalAddress = addressOptional.get();
            originalAddress.setStreet(address.getStreet());
            originalAddress.setState(address.getState());
            originalAddress.setZipcode(address.getZipcode());

            addressRepoI.save(originalAddress);
            return user;
        } else {
            addressRepoI.save(address);
            user.setAddress(address);

            userRepoI.save(user);

            return user;
        }
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
        Optional<User> optionalUser = userRepoI.findByEmailAllIgnoreCase(possession.getUser().getEmail());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addPossession(possession);

            //originally I had saveAndFlush, might just return here instead
            user = userRepoI.save(user);

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
            log.debug("retrievePortfolio: retrievePortfolio was successful");
            return userRepoI.findByEmailAllIgnoreCase(email).get().getUserPossessions();
        } else {
            throw new Exception("retrievePortfolio: retrieving stock portfolio of the user " + email + " did not go well!!!!!");
        }
    }

//    public List<StockDTO> retrievePortfolio(String email) throws Exception {
//        if (userRepoI.findByEmailAllIgnoreCase(email).isPresent()) {
//            log.debug("UserServices: retrievePortfolio was successful");
//            List<Possession> portfolio = userRepoI.findByEmailAllIgnoreCase(email).get().getUserPossessions();
//            return portfolio.stream().map(index -> index.getStock()).map(stock -> new StockDTO(stock.getInvestmentName(), stock.getTicker(), stock.getPrice(), stock.getDescription())).toList();
//        } else {
//            throw new Exception("retrievePortfolio: retrieving stock portfolio of the user " + email + " did not go well!!!!!");
//        }
//    }
}
