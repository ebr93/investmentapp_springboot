package org.perscholas.investmentapp.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.*;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.*;
import org.perscholas.investmentapp.security.AppUserDetailService;
import org.perscholas.investmentapp.services.PossessionServices;
import org.perscholas.investmentapp.services.StockServices;
import org.perscholas.investmentapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@Slf4j
@SessionAttributes("currentUser")
public class HomeController {
    private final AddressRepoI addressRepoI;
    private final UserRepoI userRepoI;
    private final StockRepoI stockRepoI;
    private final PossessionRepoI possessionRepoI;

    private final PossessionServices possessionServices;
    private final UserServices userServices;
    private final StockServices stockServices;
    private final AuthGroupRepoI authGroupRepoI;
    private final AppUserDetailService appUserDetailService;

    @Autowired
    public HomeController(UserRepoI userRepoI, StockRepoI stockRepoI, PossessionRepoI possessionRepoI,
                          AddressRepoI addressRepoI, PossessionServices possessionServices,
                          UserServices userServices, StockServices stockServices,
                          AuthGroupRepoI authGroupRepoI, AppUserDetailService appUserDetailService) {

        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
        this.addressRepoI = addressRepoI;
        this.possessionServices = possessionServices;
        this.userServices = userServices;
        this.stockServices = stockServices;
        this.authGroupRepoI = authGroupRepoI;
        this.appUserDetailService =appUserDetailService;

    }

    @GetMapping("/index")
    public String homePage(Model model){
        log.info("I am in the index controller method");

        return "index";
    }

    @GetMapping("/dashboard")
    public String dashPage(Model model) throws Exception {
        log.warn("/dashboard");

        List<StockDTO> allStocks = stockServices.allStocks();
        model.addAttribute("allStocks", allStocks);

        User myUser = userRepoI.findById(1).get();
        model.addAttribute("myUser", myUser);

        List<Possession> userPortfolio = userServices.retrievePortfolio(myUser.getEmail());
        model.addAttribute("userPortfolio", userPortfolio);
        //allStocks.forEach((s) -> System.out.println(s));

        log.warn("/dashboard: models have been developed");

        return "dashboard";
    }

    @PostMapping("/dashboard/addstock")
    public String addStock(@RequestParam("ticker") String ticker,
                           @RequestParam("shares") double shares) throws Exception {
        log.warn("/dashboard/addstock: add stock has initialized");
        User userOne = userRepoI.findById(1).get();
        Stock stock = stockRepoI.findByTicker(ticker).get();
        Possession possession = new Possession(shares, userOne, stock);

        possessionServices.createOrUpdate(possession, userOne, stock);
        log.warn("/dashboard/addstock: stock has been added to user 1");

        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPageValidation(Model model) {
//        model.addAttribute("user", new User());
        log.warn("/login: I am in login page");

        return "login";
    }

    @GetMapping("/signup")
    public String userForm(Model model) {
        model.addAttribute("user", new User());

        log.warn("/signup: model completed");
        return "signup";
    }

    @PostMapping("/signup")
    public String userProcess(@Valid @ModelAttribute("user") User user,
                              BindingResult bindingResult,
                              @RequestParam("street") String street,
                              @RequestParam("state") String state,
                              @RequestParam("zip") int zip) {
        // this works, but won't login new user

        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getAllErrors().toString());
            return "signup";
        }

        user = userServices.createOrUpdateRunning(user);
        user = userServices.addOrUpdateAddress(new Address(street, state, zip), user);
        AuthGroup newAuth = new AuthGroup(user.getEmail(), "ROLE_USER");

        authGroupRepoI.saveAndFlush(newAuth);

        log.warn("/signup: user successfully signed up");
        log.warn(user.toString());
        return "redirect:/login";
    }

    @GetMapping("/403")
    public String userExceptionHandlePage(){
        return "403";
    }

    @GetMapping("/error")
    public String exceptionHandlePage(){
        return "error";
    }

    @GetMapping("/user/{id}")
    public String getUserWithID(@PathVariable(name = "id") int id,
                                Model model) {
        log.warn(String.valueOf(id));
        log.warn(userRepoI.findById(id).toString());
        User user = userRepoI.findById(id).get();
        model.addAttribute("currenUser", user);

        List<StockDTO> allStocks = stockServices.allStocks();

        model.addAttribute("allStocks", allStocks);
        allStocks.forEach((s) -> System.out.println(s));

        return "userportfolio";
    }
}
