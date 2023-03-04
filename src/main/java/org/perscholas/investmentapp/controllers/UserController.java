package org.perscholas.investmentapp.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.AuthGroup;
import org.perscholas.investmentapp.models.Possession;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.perscholas.investmentapp.security.AppUserPrincipal;
import org.perscholas.investmentapp.services.PossessionServices;
import org.perscholas.investmentapp.services.StockServices;
import org.perscholas.investmentapp.services.UserServices;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes(value = {"currentUser"})
@RequestMapping("/user")
// @SessionAttributes(value = {"msg"})
class UserController {
    private final AddressRepoI addressRepoI;
    private final UserRepoI userRepoI;
    private final StockRepoI stockRepoI;
    private final PossessionRepoI possessionRepoI;

    private final UserServices userServices;
    private final StockServices stockServices;
    private final PossessionServices possessionServices;

    public UserController(AddressRepoI addressRepoI, UserRepoI userRepoI,
                          StockRepoI stockRepoI, PossessionRepoI possessionRepoI,
                          UserServices userServices, StockServices stockServices,
                          PossessionServices possessionServices) {
        this.addressRepoI = addressRepoI;
        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
        this.userServices = userServices;
        this.stockServices = stockServices;
        this.possessionServices = possessionServices;
    }


//    @GetMapping("/dashboard")
//    public String getUserWithID(@ModelAttribute("currentUser") User user,
//                                    Model model) throws Exception {
//        log.warn(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        AppUserPrincipal authorizedUser = (AppUserPrincipal)principal;
//        if (authorizedUser.getUsername() != null) {
//            User currentUser = userRepoI.findByEmailAllIgnoreCase(authorizedUser.getUsername()).get();
//
//            model.addAttribute("currentUser", currentUser);
//            log.warn("/user/dashboard: User logged is " + String.valueOf(currentUser.getEmail()));
//
//            List<StockDTO> allStocks = stockServices.allStocks();
//            model.addAttribute("allStocks", allStocks);
//
//            List<Possession> userPortfolio = userServices.retrievePortfolio(currentUser.getEmail());
//            model.addAttribute("userPortfolio", userPortfolio);
//
//            allStocks.forEach((s) -> System.out.println(s));
//        } else {
//            throw new Exception("/user/dashboard: Principal was not an instance of AppUserPrincipal");
//        }
//        return "userdashboard";
//    }

    @GetMapping("/dashboard")
    public String getUserWithID(@ModelAttribute("currentUser") User user,
                                Model model,
                                HttpSession http) throws Exception {
        log.warn("the value of CurrentUser is " + user.toString());
        log.warn("the attr of session theStudent in model is " + http.getAttribute("currentUser"));

        if (user != null) {

            List<StockDTO> allStocks = stockServices.allStocks();
            model.addAttribute("allStocks", allStocks);

            List<Possession> userPortfolio = userServices.retrievePortfolio(user.getEmail());
            model.addAttribute("userPortfolio", userPortfolio);

            //allStocks.forEach((s) -> System.out.println(s));
        } else {
            throw new Exception("/user/dashboard: Principal was not an instance of AppUserPrincipal");
        }
        return "userdashboard";
    }

    @PostMapping("/dashboard/addstock")
    public String addStock(@ModelAttribute("currentUser") User user,
                            @RequestParam("ticker") String ticker,
                            @RequestParam("shares") double shares) throws Exception {
        log.warn("/user/dashboard/addstock: add stock has initialized");
        log.warn("/user/dashboard/addstock: " + user);
        log.warn("/user/dashboard/addstock: " + user.getEmail());
        Stock stock = stockRepoI.findByTicker(ticker).get();
        Possession possession = new Possession(shares, user, stock);

        possessionServices.createOrUpdate(possession, user, stock);
        log.warn("user/dashboard/addstock: stock has been added to user 1");

        return "redirect:/user/dashboard";
    }

    @GetMapping("/portfolio")
    public String portfolio(@ModelAttribute("currentUser") User user,
                                Model model,
                                HttpSession http) throws Exception {
        log.warn("the value of CurrentUser is " + user.toString());
        log.warn("the attr of session theStudent in model is " + http.getAttribute("currentUser"));

        if (user != null) {

//            List<StockDTO> allStocks = stockServices.allStocks();
//            model.addAttribute("allStocks", allStocks);

            List<Possession> userPortfolio = userServices.retrievePortfolio(user.getEmail());
            model.addAttribute("userPortfolio", userPortfolio);

            //allStocks.forEach((s) -> System.out.println(s));
        } else {
            throw new Exception("/user/portfolio : Principal was not an instance of AppUserPrincipal");
        }
        return "userportfolio";
    }

//    @GetMapping("/portfolio/{id}")
//    public String getUserWithID(@PathVariable(name = "id") int id,
//                                Model model) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof AuthGroup) {
//
//        }
//
//        log.warn(String.valueOf(id));
//        log.warn(userRepoI.findById(id).toString());
//        User user = userRepoI.findById(id).get();
//        model.addAttribute("currenUser", user);
//
//        List<StockDTO> allStocks = stockServices.allStocks();
//
//        model.addAttribute("allStocks", allStocks);
//        allStocks.forEach((s) -> System.out.println(s));
//
//        return "userportfolio";
//    }

}
