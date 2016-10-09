package com.aristotle.admin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserViewController {

    @RequestMapping(value = {"/home", "/index.html"})
    public String userHome(ModelAndView mv, HttpServletRequest request) {
        return "home";
    }
}
