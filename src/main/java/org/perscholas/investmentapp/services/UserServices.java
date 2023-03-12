package org.perscholas.investmentapp.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.*;

import org.perscholas.investmentapp.models.*;


import org.perscholas.investmentapp.security.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserServices {
    private final StockRepoI stockRepoI;
    private final AuthGroupRepoI authGroupRepoI;
    UserRepoI userRepoI;
    PossessionRepoI possessionRepoI;
    AddressRepoI addressRepoI;
    AppUserDetailService appUserDetailService;


    @Autowired
    public UserServices(UserRepoI userRepoI, PossessionRepoI possessionRepoI,
                        AuthGroupRepoI authGroupRepoI, AddressRepoI addressRepoI,
                        AppUserDetailService appUserDetailService,
                        StockRepoI stockRepoI) {
        this.userRepoI = userRepoI;
        this.possessionRepoI = possessionRepoI;
        this.authGroupRepoI = authGroupRepoI;
        this.addressRepoI = addressRepoI;
        this.appUserDetailService = appUserDetailService;

        this.stockRepoI = stockRepoI;
    }

    public User createOrUpdate(User user) {
        Optional<User> userOptional = userRepoI.findByEmailAllIgnoreCase(user.getEmail());

        if (userOptional.isPresent() || user.getId() != null) {
            log.warn("createOrUpdate(): user with email " + user.getEmail() +
                    " already exists");
            User originalUser = userOptional.get();

            originalUser.setFirstName(user.getFirstName());
            originalUser.setLastName(user.getLastName());
            originalUser.setEmail(user.getEmail());

            log.warn("createOrUpdate(): user with email " + user.getEmail() +
                    " already exists");

            return userRepoI.save(originalUser);
        } else {
            log.debug("createOrUpdate(): user with email " + user.getEmail() + " has been created");

//            user.setPassword(user.getPassword());

            AuthGroup newAuth = new AuthGroup(user.getEmail(), "ROLE_USER");
            authGroupRepoI.save(newAuth);

            return userRepoI.save(user);
        }
    }

    public User createOrUpdate(User user, User editUser) {
        Optional<User> userOptional = userRepoI.findByEmailAllIgnoreCase(user.getEmail());

        if (userOptional.isPresent() || user.getId() != null) {
            log.debug("createOrUpdate(): user with email " + user.getEmail() + " already exists");
            User originalUser = userOptional.get();

            originalUser.setFirstName(editUser.getFirstName());
            originalUser.setLastName(editUser.getLastName());
            originalUser.setEmail(editUser.getEmail());

            log.debug("createOrUpdate(): user with email " + user.getEmail() + " already exists");

            return userRepoI.save(originalUser);
        } else {
            log.debug("createOrUpdate(): user with email " + user.getEmail() + " has been created");

//            user.setPassword(user.getPassword());

            AuthGroup newAuth = new AuthGroup(user.getEmail(), "ROLE_USER");
            authGroupRepoI.save(newAuth);

            return userRepoI.save(user);
        }
    }

    // probably just have to make this one a create only, for /signup
    public User createOrUpdateRunning(User user) {
        Optional<User> userOptional = userRepoI.findByEmailAllIgnoreCase(user.getEmail());

        if (userOptional.isPresent()) {
            log.debug("UserServices: user with email " + user.getEmail() + " already exists");
            User originalUser = userOptional.get();
            originalUser.setFirstName(user.getFirstName());
            originalUser.setLastName(user.getLastName());

//            originalUser.setEmail(user.getEmail());

            return userRepoI.save(originalUser);
        } else {
            log.debug("UserServices: user with email " + user.getEmail() + " has been created");

            return userRepoI.save(user);
        }
    }

//    public User createAuthRunning(User user) {
//        AuthGroup newAuth = new AuthGroup(user.getEmail(), "ROLE_USER");
//        authGroupRepoI.saveAndFlush(newAuth);
//        AppUserPrincipal appUserPrincipal = new AppUserPrincipal(user, authGroupRepoI.findAll());
////        appUserPrincipal.getAuthorities();
////        appUserDetailService.loadUserByUsername(user.getEmail());
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(newAuth, user.getPassword(), appUserPrincipal.getAuthorities()));
//        log.warn("createAuthRunning: ");
//
//        return user;
//    }

    public User addOrUpdateAddress(Address address, User user) {
        Optional<Address> addressOptional = addressRepoI.findById(address.getId());
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

    public User deletePossesionToUser(Stock stock, User user) throws Exception {
        Optional<Possession> userPossession = possessionRepoI.findByUserAndStock(user, stock);
        Optional<User> optionalUser = userRepoI.findByEmailAllIgnoreCase(user.getEmail());

        if(userPossession.isPresent() && optionalUser.isPresent()) {

            User confirmedUser = optionalUser.get();
            Possession confirmedPossession = userPossession.get();
            confirmedUser.removePossession(confirmedPossession);
            stock.removePossession(confirmedPossession);

            confirmedUser = userRepoI.saveAndFlush(confirmedUser);
            stockRepoI.saveAndFlush(stock);
            possessionRepoI.delete(confirmedPossession);

            return userRepoI.save(confirmedUser);
        } else {
            throw new Exception("removing a possession to the user " + user.getEmail() + " did not go well!!!!!");
        }
    }

    public User deletePossesionToUser(Possession possession) throws Exception {
        Optional<Possession> userPossession =
                possessionRepoI.findById(possession.getId());
        Optional<User> optionalUser =
                userRepoI.findByEmailAllIgnoreCase(possession.getUser().getEmail());

        if(userPossession.isPresent() && optionalUser.isPresent()) {

            User confirmedUser = optionalUser.get();
            Possession confirmedPossession = userPossession.get();
            confirmedUser.removePossession(confirmedPossession);
            possession.getStock().removePossession(confirmedPossession);

            confirmedUser = userRepoI.saveAndFlush(confirmedUser);
            stockRepoI.saveAndFlush(possession.getStock());
            possessionRepoI.delete(confirmedPossession);

            return userRepoI.save(confirmedUser);
        } else {
            throw new Exception("removing a possession to stock " + possession.getStock().getStockName() + " " +
                    "did not go well!!!!!");
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

}
