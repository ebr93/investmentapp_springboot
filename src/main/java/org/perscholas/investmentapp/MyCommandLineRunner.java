package org.perscholas.investmentapp;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.*;

import org.perscholas.investmentapp.models.*;
import org.perscholas.investmentapp.services.PossessionServices;
import org.perscholas.investmentapp.services.StockServices;
import org.perscholas.investmentapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {
    UserRepoI userRepoI;
    StockRepoI stockRepoI;
    PossessionRepoI possessionRepoI;
    AddressRepoI addressRepoI;
    PossessionServices possessionServices;
    AuthGroupRepoI authGroupRepoI;
    UserServices userServices;
    StockServices stockServices;


    @Autowired
    public MyCommandLineRunner(UserRepoI userRepoI, StockRepoI stockRepoI,
                                PossessionRepoI possessionRepoI, AddressRepoI addressRepoI,
                                PossessionServices possessionServices, AuthGroupRepoI authGroupRepoI,
                                UserServices userServices, StockServices stockServices) {
        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
        this.addressRepoI = addressRepoI;
        this.possessionServices = possessionServices;
        this.userServices = userServices;
        this.stockServices = stockServices;
        this.authGroupRepoI = authGroupRepoI;
    }

    @PostConstruct
    void created(){
        log.warn("=============== My CommandLineRunner Got Created ===============");
    }

    @Override
    public void run(String... args) throws Exception {
        Address address1 = new Address("123 Main St", "CA", 12345);
        Address address2 = new Address("456 Elm St", "NY", 10001);
        Address address3 = new Address("789 Oak St", "TX", 75001);
        Address address4 = new Address("987 Pine St", "FL", 33428);
        Address address5 = new Address("654 Cedar Ave", "IL", 60601);
        addressRepoI.saveAndFlush(address1);
        addressRepoI.saveAndFlush(address2);
//        addressRepoI.saveAndFlush(address3);
//        addressRepoI.saveAndFlush(address4);
//        addressRepoI.saveAndFlush(address5);



        User user1 = new User("Edward", "Barcenas", "email@email.com", "Hello1234!");
        User user2 = new User("Jane", "Doe", "janedoe@example.com", "Hello1234!");
        User user3 = new User("Bob", "Smith", "bobsmith@example.com", "Hello1234!");
        User user4 = new User("Alice", "Johnson", "alicejohnson@example.com", "Hello1234!");
        User user5 = new User("Sam", "Brown", "sambrown@example.com", "Hello1234!");
//        user1.setPassword(user1.getPassword());
//        user2.setPassword(user2.getPassword());
//        user3.setPassword(user3.getPassword());
//        user4.setPassword(user4.getPassword());
//        user5.setPassword(user5.getPassword());
        userRepoI.saveAndFlush(user1);
        userRepoI.saveAndFlush(user2);
//        userRepoI.saveAndFlush(user3);
//        userRepoI.saveAndFlush(user4);
//        userRepoI.saveAndFlush(user5);
        user1.setAddress(address1);
        user2.setAddress(address2);
//        user3.setAddress(address3);
//        user4.setAddress(address4);
//        user5.setAddress(address5);
        userRepoI.saveAndFlush(user1);
        userRepoI.saveAndFlush(user2);
//        userRepoI.saveAndFlush(user3);
//        userRepoI.saveAndFlush(user4);
//        userRepoI.saveAndFlush(user5);
        userServices.createOrUpdate(user3);
        userServices.createOrUpdate(user4);
        userServices.createOrUpdate(user5);

        userServices.addOrUpdateAddress(address3, user3);
        userServices.addOrUpdateAddress(address4, user4);
        userServices.addOrUpdateAddress(address5, user5);

        AuthGroup authGroup1 = new AuthGroup("janedoe@example.com", "ROLE_ADMIN");
        AuthGroup authGroup2 = new AuthGroup("email@email.com", "ROLE_ADMIN");
        AuthGroup authGroup3 = new AuthGroup("bobsmith@example.com", "ROLE_USER");
        AuthGroup authGroup4 = new AuthGroup("alicejohnson@example.com", "ROLE_USER");
        AuthGroup authGroup5 = new AuthGroup("sambrown@example.com", "ROLE_USER");
        authGroupRepoI.save(authGroup1);
        authGroupRepoI.save(authGroup2);
//        authGroupRepoI.save(authGroup3);
//        authGroupRepoI.save(authGroup4);
//        authGroupRepoI.save(authGroup5);



        Stock investment1 = new Stock("Apple Inc.", "AAPL",
                BigDecimal.valueOf(128.28),
                "Technology company.");
        Stock investment2 = new Stock("Microsoft Corporation", "MSFT",
                BigDecimal.valueOf(240.18), "Technology company.");
        Stock investment3 = new Stock("Amazon.com, Inc.", "AMZN",
                BigDecimal.valueOf(3127.47), "Technology and retail company.");
        Stock investment4 = new Stock("Alphabet Inc.", "GOOGL",
                BigDecimal.valueOf(2075.95), "Technology company.");
        Stock investment5 = new Stock("Facebook, Inc.", "FB",
                BigDecimal.valueOf(264.28), "Social media and technology " +
                "company.");
        Stock investment6 = new Stock("Tesla, Inc.", "TSLA",
                BigDecimal.valueOf(789.30), "Electric vehicle and clean " +
                "energy company.");
        Stock investment7 = new Stock("Johnson & Johnson", "JNJ",
                BigDecimal.valueOf(165.89), "Pharmaceutical and consumer " +
                "goods company.");
        Stock investment8 = new Stock("JPMorgan Chase & Co.", "JPM",
                BigDecimal.valueOf(152.14), "Investment banking and financial" +
                " services.");
        Stock investment9 = new Stock("Exxon Mobil Corporation", "XOM",
                BigDecimal.valueOf(56.23), "Oil and gas company.");
        Stock investment10 = new Stock("Berkshire Hathaway Inc.", "BRK.A",
                BigDecimal.valueOf(408548.00), "Conglomerate holding company.");
        Stock investment11 = new Stock("Visa Inc.", "V",
                BigDecimal.valueOf(206.72), "Financial services company.");
        Stock investment12 = new Stock("Walmart Inc.", "WMT",
                BigDecimal.valueOf(138.61), "Retail corporation.");
        Stock investment13 = new Stock("Johnson Controls International plc",
                "JCI", BigDecimal.valueOf(65.88), "Diversified technology and" +
                " industrial company.");
        Stock investment14 = new Stock("McDonald's Corporation", "MCD",
                BigDecimal.valueOf(209.64), "Fast food restaurant chain.");
        Stock investment15 = new Stock("The Goldman Sachs Group, Inc.", "GS",
                BigDecimal.valueOf(348.15), "Investment banking and financial" +
                " services.");
        Stock investment16 = new Stock("Intel Corporation", "INTC",
                BigDecimal.valueOf(63.62), "Technology company.");
        Stock investment17 = new Stock("Cisco Systems, Inc.", "CSCO",
                BigDecimal.valueOf(45.95), "Networking and IT company.");
        Stock investment18 = new Stock("Walt Disney Co.", "DIS",
                BigDecimal.valueOf(185.27), "Entertainment and media company.");
        Stock investment19 = new Stock("Pfizer Inc.", "PFE",
                BigDecimal.valueOf(35.77), "Pharmaceuticals company.");
        Stock investment20 = new Stock("Coca-Cola Consolidated, Inc.", "COKE"
                , BigDecimal.valueOf(345.38), "Soft drink bottling company.");
        Stock investment21 = new Stock("Verizon Communications Inc.", "VZ",
                BigDecimal.valueOf(56.60), "Telecommunications company.");
        Stock investment22 = new Stock("UnitedHealth Group Incorporated",
                "UNH", BigDecimal.valueOf(350.23), "Healthcare services and " +
                "insurance company.");
        Stock investment23 = new Stock("Procter & Gamble Co.", "PG",
                BigDecimal.valueOf(129.84), "Consumer goods company.");
        Stock investment24 = new Stock("The Home Depot, Inc.", "HD",
                BigDecimal.valueOf(293.18), "Home improvement retailer.");
        Stock investment25 = new Stock("General Electric Company", "GE",
                BigDecimal.valueOf(12.52), "Multinational conglomerate.");
        stockRepoI.saveAndFlush(investment1);
        stockRepoI.saveAndFlush(investment2);
        stockRepoI.saveAndFlush(investment3);
        stockRepoI.saveAndFlush(investment4);
        stockRepoI.saveAndFlush(investment5);
        stockRepoI.saveAndFlush(investment6);
        stockRepoI.saveAndFlush(investment7);
        stockRepoI.saveAndFlush(investment8);
        stockRepoI.saveAndFlush(investment9);
        stockRepoI.saveAndFlush(investment10);
        stockRepoI.saveAndFlush(investment11);
        stockRepoI.saveAndFlush(investment12);
        stockRepoI.saveAndFlush(investment13);
        stockRepoI.saveAndFlush(investment14);
        stockRepoI.saveAndFlush(investment15);
        stockRepoI.saveAndFlush(investment16);
        stockRepoI.saveAndFlush(investment17);
        stockRepoI.saveAndFlush(investment18);
        stockRepoI.saveAndFlush(investment19);
        stockRepoI.saveAndFlush(investment20);
        stockRepoI.saveAndFlush(investment21);
        stockRepoI.saveAndFlush(investment22);
        stockRepoI.saveAndFlush(investment23);
        stockRepoI.saveAndFlush(investment24);
        stockRepoI.saveAndFlush(investment25);

        Possession us5 = new Possession(35.0, user1, investment3);
        Possession us1 = new Possession(20.0, user1, investment4);
        Possession us2 = new Possession(10.0, user1, investment5);
        Possession us3 = new Possession(15.0, user1, investment6);
        Possession us4 = new Possession(85.0, user1, investment7);
        Possession us6 = new Possession(15.0, user2, investment3);
        Possession us7 = new Possession(5.0, user3, investment4);
        Possession us8 = new Possession(55.0, user2, investment5);
        Possession us9 = new Possession(25.0, user2, investment6);
        Possession us0 = new Possession(3.0, user3, investment7);

//        possessionServices.addingNewPosition(us);
//        possessionServices.addingNewPosition(us0);
//        possessionServices.addingNewPosition(us1);
//        possessionServices.addingNewPosition(us2);
//        possessionServices.addingNewPosition(us3);
//        possessionServices.addingNewPosition(us4);
//        possessionServices.addingNewPosition(us6);
//        possessionServices.addingNewPosition(us7);
//        possessionServices.addingNewPosition(us8);
//        possessionServices.addingNewPosition(us9);
        possessionServices.createOrUpdate(us1);
        possessionServices.createOrUpdate(us2);
        possessionServices.createOrUpdate(us3);
        possessionServices.createOrUpdate(us4);
        possessionServices.createOrUpdate(us5);
        possessionServices.createOrUpdate(us6);
        possessionServices.createOrUpdate(us7);
        possessionServices.createOrUpdate(us8);
        possessionServices.createOrUpdate(us9);
        possessionServices.createOrUpdate(us0);
//        userServices.savePossessionToUser(us1);
//        userServices.savePossessionToUser(us2);
//        userServices.savePossessionToUser(us3);
//        userServices.savePossessionToUser(us4);
//        userServices.savePossessionToUser(us5);
//        userServices.savePossessionToUser(us6);
//        userServices.savePossessionToUser(us7);
//        userServices.savePossessionToUser(us8);
//        userServices.savePossessionToUser(us9);
//        userServices.savePossessionToUser(us0);
//        stockServices.savePositionToStock(us1.getStock().getId(), us1.getId());
//        stockServices.savePositionToStock(us2.getStock().getId(), us2.getId());
//        stockServices.savePositionToStock(us3.getStock().getId(), us3.getId());
//        stockServices.savePositionToStock(us4.getStock().getId(), us4.getId());
//        stockServices.savePositionToStock(us5.getStock().getId(), us5.getId());
//        stockServices.savePositionToStock(us6.getStock().getId(), us6.getId());
//        stockServices.savePositionToStock(us7.getStock().getId(), us7.getId());
//        stockServices.savePositionToStock(us8.getStock().getId(), us8.getId());
//        stockServices.savePositionToStock(us9.getStock().getId(), us9.getId());
//        stockServices.savePositionToStock(us0.getStock().getId(), us0.getId());
//


    }
}
