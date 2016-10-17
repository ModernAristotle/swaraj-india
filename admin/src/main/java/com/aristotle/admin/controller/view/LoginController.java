package com.aristotle.admin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping(value = "/login.html")
    public String login(ModelAndView mv, HttpServletRequest request) {
        return "loginTemplate";
    }

    @RequestMapping(value = "/loginView.html")
    public String loginView(ModelAndView mv, HttpServletRequest request) {
        return "login";
    }
}
