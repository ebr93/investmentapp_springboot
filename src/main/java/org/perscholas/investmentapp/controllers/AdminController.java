package org.perscholas.investmentapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.*;
import org.perscholas.investmentapp.models.Possession;
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

        return "adminusers";
    }

    @GetMapping("/users/edit/{email}")
    public String modifyUser(@ModelAttribute("currentUser")User user,
                           Model model) {
        List<User> usersList =
                userRepoI.findAll();

        model.addAttribute("allUsers", usersList);

        return "adminusers";
    }

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

            userRepoI.delete(user);
        } else {
            throw new Exception("/admin/users/delete: email " + email +
                    " was not valid");
        }
        return "redirect:/admin/users";
    }


}
