package org.perscholas.investmentapp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.models.Address;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.perscholas.investmentapp.services.StockServices;
import org.perscholas.investmentapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InvestmentappApplicationTests {
	@Autowired
	UserServices userServices;
	@Autowired
	StockServices stockServices;
	@Autowired
	private UserRepoI userRepoI;
	@Autowired
	private StockRepoI	stockRepoI;

	static User testUser1 = new User(1, "Edward", "Barcenas", "email@email.com",
			"Hello1234!");
	static Address testAddress1 = new Address(1, "123 Main St", "CA", 12345);

	static List<User> expectedUsers() {
		User user1 = new User(1, "Edward", "Barcenas", "email@email.com",
				"Hello1234!");
		User user2 = new User(2, "Jane", "Doe", "janedoe@example.com",
				"Hello1234!");
		User user3 = new User(3,"Bob", "Smith", "bobsmith@example.com",
				"Hello1234!");
		User user4 = new User(4, "Alice", "Johnson", "alicejohnson@example.com"
				, "Hello1234!");
		User user5 = new User(5,"Sam", "Brown", "sambrown@example.com",
				"Hello1234!");

		Address address1 = new Address("123 Main St", "CA", 12345);
		Address address2 = new Address("456 Elm St", "NY", 10001);
		Address address3 = new Address("789 Oak St", "TX", 75001);
		Address address4 = new Address("987 Pine St", "FL", 33428);
		Address address5 = new Address("654 Cedar Ave", "IL", 60601);
		user1.setAddress(address1);
		user2.setAddress(address2);
		user3.setAddress(address3);
		user4.setAddress(address4);
		user5.setAddress(address5);

		List<User> expected = new ArrayList<>();
		expected.add(user1);
		expected.add(user2);
		expected.add(user3);
		expected.add(user4);
		expected.add(user5);

		return expected;
	}

	static List<Stock> expectedStocks() {
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

		List<Stock> expectedStocks = new ArrayList<>();
		expectedStocks.add(investment1);
		expectedStocks.add(investment2);
		expectedStocks.add(investment3);
		expectedStocks.add(investment4);
		expectedStocks.add(investment5);
		expectedStocks.add(investment6);
		expectedStocks.add(investment7);
		expectedStocks.add(investment8);
		expectedStocks.add(investment9);
		expectedStocks.add(investment10);
		expectedStocks.add(investment11);
		expectedStocks.add(investment12);
		expectedStocks.add(investment13);
		expectedStocks.add(investment14);
		expectedStocks.add(investment15);
		expectedStocks.add(investment16);
		expectedStocks.add(investment17);
		expectedStocks.add(investment18);
		expectedStocks.add(investment19);
		expectedStocks.add(investment20);
		expectedStocks.add(investment21);
		expectedStocks.add(investment22);
		expectedStocks.add(investment23);
		expectedStocks.add(investment24);
		expectedStocks.add(investment25);

		return expectedStocks;
	}

	@Test @Order(1)
	void testUserEmail() throws Exception {
		assertThat(userRepoI.findByEmail(testUser1.getEmail()).get().getEmail()).isEqualTo(testUser1.getEmail());
	}

	@Test @Order(2)
	void testUserFirstName() throws Exception {
		assertThat(userRepoI.findByEmail(testUser1.getEmail()).get().getFirstName()).isEqualTo(testUser1.getFirstName());
	}

	@Test @Order(3)
	void testUserLastName() throws Exception {
		assertThat(userRepoI.findByEmail(testUser1.getEmail()).get().getLastName()).isEqualTo(testUser1.getLastName());
	}

	@Test @Order(4)
	void testUserAddress() throws Exception {
		assertThat(userRepoI.findByEmail(testUser1.getEmail()).get().getAddress()).isEqualTo(testAddress1);
	}

	@Test @Order(5)
	void testAllStocks() throws Exception {
		assertThat(stockServices.allRegularStocks()).isEqualTo(expectedStocks());
	}

}
