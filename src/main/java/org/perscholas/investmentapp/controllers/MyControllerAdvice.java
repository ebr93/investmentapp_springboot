package org.perscholas.investmentapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.investmentapp.dao.UserRepoI;
import org.perscholas.investmentapp.models.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {
    private final UserRepoI userRepoI;

    public MyControllerAdvice(UserRepoI userRepoI) {
        this.userRepoI = userRepoI;
    }

    @ExceptionHandler(Exception.class)
    public RedirectView exceptionHandle(Exception ex){

        log.debug("something happened");
        ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/index");
        return redirectView;
    }

    // Session Attribute Original kind of worked
    // used for User logged in
//    @ModelAttribute
//    public void currentUser(Model model) {
//        model.addAttribute("currentUser", new User());
//    }

//    @ModelAttribute
//    public User currentUser(Model model) {
//        return new User();
//    }

    @ModelAttribute
    public void loggedInUser(Model model, HttpServletRequest request, HttpSession http){
        Principal p = request.getUserPrincipal();

        User user = null;
        if(p != null){
            user =  userRepoI.findByEmail(p.getName()).get();
            http.setAttribute("currentUser", user);
            log.warn("MyControllerAdvice: session attr theStudent in advice controller  " + user.getEmail());

        } else {
            log.warn("MyControllerAdvice: principal was null");
        }
    }


    // Session Attribute
//    @ModelAttribute
//    public void initModel(Model model) {
//        model.addAttribute("msg", "used for SessionAttribute");
//    }
}
