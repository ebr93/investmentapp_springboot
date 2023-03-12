package org.perscholas.investmentapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.*;
import org.perscholas.investmentapp.models.AuthGroup;
import org.perscholas.investmentapp.models.Possession;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.perscholas.investmentapp.services.PossessionServices;
import org.perscholas.investmentapp.services.StockServices;
import org.perscholas.investmentapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@SessionAttributes(value = {"currentUser"})
@RequestMapping("/admin")
public class AdminController {
    private final static String REDIRECT_STOCKS_PAGE = "redirect:/admin/stocks";
    private final AuthGroupRepoI authGroupRepoI;
    private final AddressRepoI addressRepoI;
    private final UserRepoI userRepoI;
    private final StockRepoI stockRepoI;
    private final PossessionRepoI possessionRepoI;

    private final UserServices userServices;
    private final StockServices stockServices;
    private final PossessionServices possessionServices;

    @Autowired
    public AdminController(AuthGroupRepoI authGroupRepoI, AddressRepoI addressRepoI,
                           UserRepoI userRepoI, StockRepoI stockRepoI,
                           PossessionRepoI possessionRepoI, UserServices userServices,
                           StockServices stockServices, PossessionServices possessionServices) {
        this.authGroupRepoI = authGroupRepoI;
        this.addressRepoI = addressRepoI;
        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
        this.userServices = userServices;
        this.stockServices = stockServices;
        this.possessionServices = possessionServices;
    }

    @GetMapping("/users")
    public String getUsers(@ModelAttribute("currentUser")User user,
                           Model model) {
        List<User> usersList =
                userRepoI.findAll();

        model.addAttribute("allUsers", usersList);
        model.addAttribute("editUser", new User());

        return "adminusers";
    }

    @PostMapping("/users/edit")
    public String modifyUser(@ModelAttribute("editUser") User userEdit,
                             @RequestParam("user-email") String email,
                             @RequestParam("first-name") String firstName,
                             @RequestParam("last-name") String lastName) {
        Optional<User> userOptional = userRepoI.findByEmailAllIgnoreCase(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            userServices.createOrUpdate(user);
        }

        return "redirect:/admin/users";
    }

    // need to add if statement to logout admin if he deletes his account
    @PostMapping("/users/delete/{email}")
    public String deleteUser(
                             @PathVariable(name="email") String email) throws Exception {
        if (email != null) {
            log.warn("/admin/users/delete: delete user has initialized");
            log.warn("/admin/users/delete: user with email " + email +
                    "deleted");
            User user = userRepoI.findByEmail(email).get();

            // deletes all possessions, to get rid of foreign keys
            Optional<List<Possession>> userPossessions =
                    possessionRepoI.findByUser(user);
            if (userPossessions.isPresent()) {
                List<Possession> possessions = userPossessions.get();
                for (Possession p : possessions) {
                    userServices.deletePossesionToUser(p.getStock(), user);
                }
            }

            AuthGroup userAuth =
                    authGroupRepoI.findByEmail(user.getEmail()).get(0);
            authGroupRepoI.delete(userAuth);
            userRepoI.delete(user);
        } else {
            throw new Exception("/admin/users/delete: email " + email +
                    " was not valid");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/stocks")
    public String getStocks(@ModelAttribute("currentUser")User user,
                           Model model) {
        List<Stock> allStocks =
                stockRepoI.findAll();

        model.addAttribute("allStocks", allStocks);
        model.addAttribute("newStock", new Stock());

        return "adminstocks";
    }

    @PostMapping("/stocks/add")
    public String addStocks(@ModelAttribute("newStock") Stock stock) {
        stockRepoI.save(stock);
        return REDIRECT_STOCKS_PAGE;
    }

    @PostMapping("/stocks/edit")
    public String editStocks(@RequestParam("ticker")String ticker,
                             @RequestParam("price")String price,
                             @RequestParam("stockName")String stockName,
                             @RequestParam("description")String description) throws Exception {
        Optional<Stock> stockOptional = stockRepoI.findByTicker(ticker);

        if (stockOptional.isPresent()) {
            Stock originalStock = stockOptional.get();
            originalStock.setStockName(stockName);
            originalStock.setPrice(Double.valueOf(price));
            originalStock.setDescription(description);

            stockRepoI.save(originalStock);

            return REDIRECT_STOCKS_PAGE;
        } else {
            throw new Exception("/admin/stocks/edit: stock with ticker " + stockName +
                    " was not valid");
        }
    }

    // this works
    @PostMapping("/stocks/delete/{ticker}")
    public String deleteStock(
            @PathVariable(name="ticker") String ticker) throws Exception {
        Optional<Stock> stockOptional = stockRepoI.findByTicker(ticker);
        if (stockOptional.isPresent()) {
            log.warn("/admin/stock/delete: delete stock has initialized");
            Stock stock = stockOptional.get();
            // deletes all possessions, to get rid of foreign keys
            Optional<List<Possession>> stockUsage =
                    possessionRepoI.findByStock(stock);

            if (stockUsage.isPresent()) {
                List<Possession> possessions = stockUsage.get();
                for (Possession p : possessions) {
                    userServices.deletePossesionToUser(p);
                }
            }

            stockRepoI.delete(stock);
            log.warn("/admin/stock/delete: stock with ticker " + ticker + " " +
                    "was deleted");
        } else {
            throw new Exception("/admin/stock/delete: stock with ticker " + ticker +
                    " was not deleted");
        }
        return REDIRECT_STOCKS_PAGE;
    }

}
