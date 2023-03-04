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
@SessionAttributes("currentUser")
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
        model.addAttribute("user", new User());
        log.warn("/login: I am in login page");

        return "login";
    }

    // verifies if email and password exist within database, if so login
    @PostMapping("/login/process")
    public String loginProcess(@ModelAttribute("user") User user) {
        Optional<User> confirmUser = userRepoI.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (confirmUser == null) {
            return "/login: user login was not successful, user was null";
        } else {
            log.warn("/login: user" + confirmUser.get().getEmail() + "has successfully logged in");
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/signup")
    public String userForm(Model model) {
        model.addAttribute("user", new User());

        log.warn("/signup: model completed");
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
        userRepoI.save(user);

        log.warn("/signup: user successfully signed up");
        log.warn(user.toString());
        return "redirect:/login";
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
