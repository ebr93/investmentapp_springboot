package org.perscholas.investmentapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.*;
import org.perscholas.investmentapp.dto.StockDTO;
import org.perscholas.investmentapp.models.*;
import org.perscholas.investmentapp.security.AppUserPrincipal;
import org.perscholas.investmentapp.services.PossessionServices;
import org.perscholas.investmentapp.services.StockServices;
import org.perscholas.investmentapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@SessionAttributes(value = {"currentUser"})
@RequestMapping("/user")
// @SessionAttributes(value = {"msg"})
class UserController {
    private final AuthGroupRepoI authGroupRepoI;
    private final AddressRepoI addressRepoI;
    private final UserRepoI userRepoI;
    private final StockRepoI stockRepoI;
    private final PossessionRepoI possessionRepoI;

    private final UserServices userServices;
    private final StockServices stockServices;
    private final PossessionServices possessionServices;

    @Autowired
    public UserController(AddressRepoI addressRepoI, UserRepoI userRepoI,
                          StockRepoI stockRepoI, PossessionRepoI possessionRepoI,
                          UserServices userServices, StockServices stockServices,
                          PossessionServices possessionServices,
                          AuthGroupRepoI authGroupRepoI) {
        this.addressRepoI = addressRepoI;
        this.userRepoI = userRepoI;
        this.stockRepoI = stockRepoI;
        this.possessionRepoI = possessionRepoI;
        this.userServices = userServices;
        this.stockServices = stockServices;
        this.possessionServices = possessionServices;
        this.authGroupRepoI = authGroupRepoI;
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

        possessionServices.createOrUpdate(possession);

        log.warn("user/portfolio/edit: possession has been edited for " + user.getEmail());

        return "redirect:/user/portfolio";
    }

    @PostMapping("/portfolio/delete/{ticker}")
    public String deletePossession(@ModelAttribute("currentUser") User user,
                                 @PathVariable(name="ticker") String ticker) throws Exception {
        if (ticker != null) {
            log.warn("/user/portfolio/delete: delete stock has initialized");
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
//        Optional<User> editUser = userRepoI.findByEmail(user.getEmail());
//        if (user != null && dbUser.isPresent()) {
        if (user != null) {
            log.warn("/user/account: CurrentUser is not null, email is " + user.getEmail());
//            model.addAttribute("editUser", new User());
            User editUser = new User();
            editUser.setFirstName(user.getFirstName());
            editUser.setLastName(user.getLastName());
            editUser.setEmail(user.getEmail());
            model.addAttribute("editUser", editUser);
            Address editAddress = addressRepoI.findById(user.getAddress().getId()).get();
            model.addAttribute("editAddress", editAddress);

        } else {
            throw new Exception("/user/account: currentUser is not logged in");
        }
        return "useraccount";
    }

    @PostMapping("/account/edit")
    public String editAccount(@ModelAttribute("currentUser") User user,
                              @Valid @ModelAttribute("editUser") User editUser,
                              BindingResult bindingResult,
                              HttpServletRequest request) throws Exception {
        Principal p = request.getUserPrincipal();
        log.warn("/user/account/edit: editUser is not null, info is " + editUser);

        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getAllErrors().toString());
            return "useraccount";
        }

        User principalUser = null;
        if (editUser != null && p != null) {
//        if (editUser != null) {
            principalUser = userRepoI.findByEmail(p.getName()).get();
            log.warn("/user/account/edit: CurrentUser(principal) is not null, email is " + principalUser.getEmail());
//            editUser.setEmail(user.getEmail());
            log.warn("/user/account/edit: editUser is not null, info is " + editUser);
            List<AuthGroup> authGroupList = authGroupRepoI.findByEmail(principalUser.getEmail());

            //authGroupRepoI.findByEmail(user.getEmail()).get(0).setEmail(editUser.getEmail());

            AuthGroup userAuth = authGroupList.get(0);
            userAuth.setEmail(editUser.getEmail());
            authGroupRepoI.saveAndFlush(userAuth);

            editUser = userServices.createOrUpdate(principalUser,editUser);

            user.setFirstName(editUser.getFirstName());
            user.setLastName(editUser.getLastName());
            user.setEmail(editUser.getLastName());

            log.warn("/user/account/edit: User " + editUser.getEmail() + " was updated");
            log.warn("/user/account/edit: Session user " + user.getEmail() + " was updated");

        } else {
            throw new Exception("/user/account/edit: currentUser is not logged in");
        }
        return "redirect:/logout";
    }

    @PostMapping("/account/edit_address")
    public String editAddress(@ModelAttribute("currentUser") User user,
                              @Valid @ModelAttribute("editAddress") Address editAddress,
                              BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getAllErrors().toString());
            return "useraccount";
        }

        if (editAddress != null && user != null) {
            log.warn("/user/account/edit_address: editAddress is not null, info is " + editAddress + " id: " + editAddress.getId());
            userServices.addOrUpdateAddress(editAddress, user);
        } else {
            throw new Exception("/user/account/edit_address: editAddress was null");
        }
        return "redirect:/user/account";
    }
}
