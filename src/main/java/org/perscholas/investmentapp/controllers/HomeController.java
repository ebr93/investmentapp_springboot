package org.perscholas.investmentapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.Address;
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
// @SessionAttributes(value = {"msg"})
public class HomeController {
    AddressRepoI addressRepoI;
    private final UserRepoI userRepoI;
    private final StockRepoI stockRepoI;
    PossessionRepoI possessionRepoI;

    PossessionServices possessionServices;
    private final UserServices userServices;
    private final StockServices stockServices;

    @Autowired
    public HomeController(UserRepoI userRepoI, StockRepoI stockRepoI, PossessionRepoI possessionRepoI,
                          AddressRepoI addressRepoI, PossessionServices possessionServices,
                          UserServices userServices, StockServices stockServices) {

        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
        this.addressRepoI = addressRepoI;
        this.possessionServices = possessionServices;
        this.userServices = userServices;
        this.stockServices = stockServices;
    }

    @GetMapping("/index")
    public String homePage(Model model){
        log.info("I am in the index controller method");
        log.warn("first call " + String.valueOf(model.getAttribute("msg")));
        model.addAttribute("msg", "Hello World IndexPage");
        log.warn("second call " + String.valueOf(model.getAttribute("msg")));

        return "index";
    }

    @GetMapping("/dashboard")
    public String dashPage(Model model, HttpServletRequest request) {
        log.warn("I am in the dashboard controller method");
        //log.warn("third call " + msg);
        //model.addAttribute("msg", "Hello World Dashboard");
        //log.warn("fourth call " + msg);

        List<StockDTO> allStocks = stockServices.allStocks();
        model.addAttribute("allStocks", allStocks);

        //allStocks.forEach((s) -> System.out.println(s));
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String addStock(@RequestParam("ticker") String ticker,
                           @RequestParam("shares") double shares) throws Exception {
        log.warn("add stock has initialized");
        User userOne = userRepoI.findById(1).get();
        Stock stock = stockRepoI.findByTicker(ticker).get();
        Possession possession = new Possession(shares, userOne, stock);

        // This also works
        possessionServices.createOrUpdate(possession);
        userServices.savePositionToUser(possession);
        stockServices.savePositionToStock(possession.getStock().getId(), possession.getId());

        // this works
        //        possessionRepoI.save(possession);
//        userOne.addPossession(possession);
//        stock.addPossession(possession);
//        userRepoI.save(userOne);
//        stockRepoI.save(stock);

        log.warn("stock has been added to user 1");

//        log.warn("user process method" + user);
//        log.warn(user.toString());
//        userRepoI.save(user);

        return "redirect:/signup";
    }

    // DELETING BECAUSE SPRING SECURITY TAKES CARE OF THESE
    // grabs the data from form
    @GetMapping("/login")
    public String loginPageValidation(Model model) {
        model.addAttribute("user", new User());
        log.warn("I am in the login-validation controller method");
        //log.warn(userRepoI.findByEmailAndPassword(email, password).toString());
        return "login";
    }

    // verifies if email and password exist within database, if so login
    @PostMapping("/login")
    public String loginProcess(@ModelAttribute("user") User user) {
        Optional<User> confirmUser = userRepoI.findByEmailAndPassword(user.getEmail(), user.getPassword());
        //log.warn(confirmUser.toString()); won't print if null
        if (confirmUser == null) {
            return "login";
        } else {
            log.warn(confirmUser.toString());
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/signup")
    public String userForm(Model model) {
        model.addAttribute("user", new User());

        log.warn("user form method");
        return "signup";
    }

    @PostMapping("/signup")
    public String userProcess(@ModelAttribute("user") User user,
                                  @RequestParam("street") String street,
                                  @RequestParam("state") String state,
                                  @RequestParam("zip") int zip) {

        Address ua = new Address(street, state, zip);
        addressRepoI.saveAndFlush(ua);
        user.setAddress(ua);
        log.warn("user process method" + user);
        log.warn(user.toString());
        userRepoI.save(user);

        return "redirect:/login";
    }

    @GetMapping("/user/{id}")
    public String getUserWithID(@PathVariable(name = "id") int id,
                                Model model) {
        log.warn(String.valueOf(id));
        log.warn(userRepoI.findById(id).toString());
        User user = userRepoI.findById(id).get();
        model.addAttribute("userValue", user);

        List<StockDTO> allStocks = stockServices.allStocks();

        model.addAttribute("allStocks", allStocks);
        allStocks.forEach((s) -> System.out.println(s));

        return "userportfolio";
    }
}
