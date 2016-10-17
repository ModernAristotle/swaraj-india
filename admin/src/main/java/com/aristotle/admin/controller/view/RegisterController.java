package com.aristotle.admin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    @RequestMapping("/register.html")
    public String registerTemplate(ModelAndView mv, HttpServletRequest request) {
        return "registerTemplate";
    }

    @RequestMapping("/registerView.html")
    public String register(ModelAndView mv, HttpServletRequest request) {
        return "register";
    }
}
