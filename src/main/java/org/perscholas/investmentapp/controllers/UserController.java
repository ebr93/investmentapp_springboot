package org.perscholas.investmentapp.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.AddressRepoI;
import org.perscholas.investmentapp.dao.PossessionRepoI;
import org.perscholas.investmentapp.dao.StockRepoI;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.*;
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

        // OG
//        possessionServices.createOrUpdate(possession, user, stock);
        possessionServices.createOrUpdate(possession);
        log.warn("user/dashboard/addstock: stock has been added to user " + user.getEmail());

        return "redirect:/user/dashboard";
    }

    @GetMapping("/portfolio")
    public String portfolio(@ModelAttribute("currentUser") User user,
                                Model model,
                                HttpSession http) throws Exception {
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

    @PostMapping("/portfolio/edit")
    public String editPossession(@ModelAttribute("currentUser") User user,
                                 @RequestParam("ticker") String ticker,
                                 @RequestParam("shares") double shares) throws Exception {

        log.warn("/user/dashboard/addstock: add stock has initialized");
        log.warn("/user/dashboard/addstock: " + user);
        log.warn("/user/dashboard/addstock: " + user.getEmail());
        Stock stock = stockRepoI.findByTicker(ticker).get();
        Possession possession = new Possession(shares, user, stock);

//      OG
//        possessionServices.createOrUpdate(possession, user, stock);
        possessionServices.createOrUpdate(possession);

        log.warn("user/portfolio/edit: possession has been edited for " + user.getEmail());

        return "redirect:/user/portfolio";
    }

    @PostMapping("/portfolio/delete/{ticker}")
    public String deletePossession(@ModelAttribute("currentUser") User user,
                                 @PathVariable(name="ticker") String ticker) throws Exception {
        if (ticker != null) {
            log.warn("/user/portfolio/delete: add stock has initialized");
            log.warn("/user/portfolio/delete: " + ticker);
            log.warn("/user/portfolio/delete: " + user);
            log.warn("/user/portfolio/delete: " + user.getEmail());

            //System.out.println(ticker.toString());

            Stock stock = stockRepoI.findByTicker(ticker).get();

            userServices.deletePossesionToUser(stock, user);
            log.warn("user/portfolio/delete: possession has been removed from user " + user.getEmail());

            return "redirect:/user/portfolio";
        } else {
            throw new Exception("/user/portfolio : Principal was not an instance of AppUserPrincipal");
        }
    }

    @GetMapping("/account")
    public String account(@ModelAttribute("currentUser") User user,
                            Model model) throws Exception {
        if (user != null) {
            log.warn("/user/account: CurrentUser is not null, email is " + user.getEmail());
            model.addAttribute("editUser", new User());
        } else {
            throw new Exception("/user/account: currentUser is not logged in");
        }
        return "useraccount";
    }

    @PostMapping("/account/edit")
    public String editAccount(@ModelAttribute("currentUser") User user,
                              @ModelAttribute("editUser") User editUser) throws Exception {
        if (editUser != null) {
            log.warn("/user/account/edit: CurrentUser is not null, email is " + user.getEmail());
            editUser.setEmail(user.getEmail());
            userServices.createOrUpdate(editUser);
            log.warn("/user/account/edit: User was updated");
        } else {
            throw new Exception("/user/account: currentUser is not logged in");
        }
        return "redirect:/user/account";
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
