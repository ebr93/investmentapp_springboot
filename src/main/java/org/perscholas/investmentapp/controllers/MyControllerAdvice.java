package org.perscholas.investmentapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

    @ExceptionHandler(Exception.class)
    public RedirectView exceptionHandle(Exception ex){

        log.debug("something happened");
        ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/signup");
        return redirectView;
    }

    // centralized in one place, so it's not copied over and over on other controllers
    @ModelAttribute
    public void initModel(Model model) {
        model.addAttribute("msg", "used for SessionAttribute");
    }
}
