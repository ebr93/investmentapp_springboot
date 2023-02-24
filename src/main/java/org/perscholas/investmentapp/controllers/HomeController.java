package org.perscholas.investmentapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.UserPositionRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.models.Address;
import org.perscholas.investmentapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Slf4j
public class HomeController {
    private final AddressRepoI addressRepoI;
    UserRepoI userRepoI;
    StockRepoI stockRepoI;
    UserPositionRepoI userPositionRepoI;

    @Autowired
    public HomeController(UserRepoI userRepoI, StockRepoI stockRepoI, UserPositionRepoI userPositionRepoI,
                          AddressRepoI addressRepoI) {

        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.userPositionRepoI = userPositionRepoI;
        this.addressRepoI = addressRepoI;
    }

    @GetMapping("/index")
    public String homePage(){
        log.info("I am in the index controller method");

        return "index";
    }

    @GetMapping("/dashboard")
    public String dashPage(){
        log.warn("I am in the dashboard controller method");
        return "dashboard";
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
                                  @RequestParam("street") String state,
                                  @RequestParam("zip") int zip) {

        Address ua = new Address(street, state, zip);
        addressRepoI.saveAndFlush(ua);
        user.setAddress(ua);
        log.warn("user process method" + user);
        log.warn(user.toString());
        userRepoI.save(user);

        return "redirect:/login";
    }
}
