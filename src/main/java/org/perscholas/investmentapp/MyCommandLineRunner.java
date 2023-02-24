package org.perscholas.investmentapp;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.StockRepoI;

import org.perscholas.investmentapp.dao.UserPositionRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.models.Address;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.perscholas.investmentapp.models.UserPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {
    UserRepoI userRepoI;
    StockRepoI stockRepoI;
    UserPositionRepoI userPositionRepoI;
    AddressRepoI addressRepoI;


    @Autowired
    public MyCommandLineRunner(UserRepoI userRepoI, StockRepoI stockRepoI, UserPositionRepoI userPositionRepoI, AddressRepoI addressRepoI) {

        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.userPositionRepoI = userPositionRepoI;
        this.addressRepoI = addressRepoI;
    }

    @PostConstruct
    void created(){
        log.warn("=============== My CommandLineRunner Got Created ===============");
    }

    @Override
    public void run(String... args) throws Exception {
        Address address1 = new Address("123 Main St", "CA", 90210);
        Address address2 = new Address("456 Elm St", "NY", 10001);
        Address address3 = new Address("789 Oak St", "TX", 75001);
        Address address4 = new Address("987 Pine St", "FL", 33428);
        Address address5 = new Address("654 Cedar Ave", "IL", 60601);

        addressRepoI.saveAndFlush(address1);
        addressRepoI.saveAndFlush(address2);
        addressRepoI.saveAndFlush(address3);
        addressRepoI.saveAndFlush(address4);
        addressRepoI.saveAndFlush(address5);



        User user1 = new User("Edward", "Barcenas",  "email@email.com", "pass123");
        User user2 = new User("Jane", "Doe", "janedoe@example.com", "Hello1234!");
        User user3 = new User("Bob", "Smith", "bobsmith@example.com", "Hello1234!");
        User user4 = new User("Alice", "Johnson", "alicejohnson@example.com", "Hello1234!");
        User user5 = new User("Sam", "Brown", "sambrown@example.com", "Hello1234!");
        userRepoI.saveAndFlush(user1);
        userRepoI.saveAndFlush(user2);
        userRepoI.saveAndFlush(user3);
        userRepoI.saveAndFlush(user4);
        userRepoI.saveAndFlush(user5);
        user1.setAddress(address1);
        user2.setAddress(address2);
        user3.setAddress(address3);
        user4.setAddress(address4);
        user5.setAddress(address5);
        userRepoI.saveAndFlush(user1);
        userRepoI.saveAndFlush(user2);
        userRepoI.saveAndFlush(user3);
        userRepoI.saveAndFlush(user4);
        userRepoI.saveAndFlush(user5);


        Stock investment1 = new Stock(1,"Apple Inc.", "AAPL", 128.28, "Technology company.");
        Stock investment2 = new Stock(2,"Microsoft Corporation", "MSFT", 240.18, "Technology company.");
        Stock investment3 = new Stock(3,"Amazon.com, Inc.", "AMZN", 3127.47, "Technology and retail company.");
        Stock investment4 = new Stock(4,"Alphabet Inc.", "GOOGL", 2075.95, "Technology company.");
        Stock investment5 = new Stock(5,"Facebook, Inc.", "FB", 264.28, "Social media and technology company.");
        Stock investment6 = new Stock(6,"Tesla, Inc.", "TSLA", 789.30, "Electric vehicle and clean energy company.");
        Stock investment7 = new Stock(7,"Johnson & Johnson", "JNJ", 165.89, "Pharmaceutical and consumer goods company.");
        Stock investment8 = new Stock(8,"JPMorgan Chase & Co.", "JPM", 152.14, "Investment banking and financial services.");
        Stock investment9 = new Stock(9,"Exxon Mobil Corporation", "XOM", 56.23, "Oil and gas company.");
        Stock investment10 = new Stock(10,"Berkshire Hathaway Inc.", "BRK.A", 408548.00, "Conglomerate holding company.");
        Stock investment11 = new Stock(11,"Visa Inc.", "V", 206.72, "Financial services company.");
        Stock investment12 = new Stock(12,"Walmart Inc.", "WMT", 138.61, "Retail corporation.");
        Stock investment13 = new Stock(13,"Johnson Controls International plc", "JCI", 65.88, "Diversified technology and industrial company.");
        Stock investment14 = new Stock(14,"McDonald's Corporation", "MCD", 209.64, "Fast food restaurant chain.");
        Stock investment15 = new Stock(15,"The Goldman Sachs Group, Inc.", "GS", 348.15, "Investment banking and financial services.");
        Stock investment16 = new Stock(16,"Intel Corporation", "INTC", 63.62, "Technology company.");
        Stock investment17 = new Stock(17,"Cisco Systems, Inc.", "CSCO", 45.95, "Networking and IT company.");
        Stock investment18 = new Stock(18,"Walt Disney Co.", "DIS", 185.27, "Entertainment and media company.");
        Stock investment19 = new Stock(19,"Pfizer Inc.", "PFE", 35.77, "Pharmaceuticals company.");
        Stock investment20 = new Stock(20,"Coca-Cola Consolidated, Inc.", "COKE", 345.38, "Soft drink bottling company.");
        Stock investment21 = new Stock(21,"Verizon Communications Inc.", "VZ", 56.60, "Telecommunications company.");
        Stock investment22 = new Stock(22,"UnitedHealth Group Incorporated", "UNH", 350.23, "Healthcare services and insurance company.");
        Stock investment23 = new Stock(23,"Procter & Gamble Co.", "PG", 129.84, "Consumer goods company.");
        Stock investment24 = new Stock(24,"The Home Depot, Inc.", "HD", 293.18, "Home improvement retailer.");
        Stock investment25 = new Stock(25,"General Electric Company", "GE", 12.52, "Multinational conglomerate.");
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

        UserPosition us = new UserPosition(35.0, user1, investment3);
        UserPosition us1 = new UserPosition(20.0, user1, investment4);
        UserPosition us2 = new UserPosition(10.0, user1, investment5);
        UserPosition us3 = new UserPosition(15.0, user1, investment6);
        UserPosition us4 = new UserPosition(85.0, user1, investment7);
        userPositionRepoI.saveAndFlush(us);
        userPositionRepoI.saveAndFlush(us1);
        userPositionRepoI.saveAndFlush(us2);
        userPositionRepoI.saveAndFlush(us3);
        userPositionRepoI.saveAndFlush(us4);

        UserPosition us6 = new UserPosition(15.0, user2, investment3);
        UserPosition us7 = new UserPosition(5.0, user3, investment4);
        UserPosition us8 = new UserPosition(55.0, user2, investment5);
        UserPosition us9 = new UserPosition(25.0, user2, investment6);
        UserPosition us0 = new UserPosition(3.0, user3, investment7);

        user1.getUserStocks().add(us);
        user1.getUserStocks().add(us1);
        user1.getUserStocks().add(us2);
        user1.getUserStocks().add(us3);
        user2.getUserStocks().add(us6);
        user2.getUserStocks().add(us7);
        user2.getUserStocks().add(us8);
        user2.getUserStocks().add(us9);
        user2.getUserStocks().add(us0);
        userRepoI.saveAndFlush(user1);
        userRepoI.saveAndFlush(user2);
        userRepoI.saveAndFlush(user3);
    }
}
