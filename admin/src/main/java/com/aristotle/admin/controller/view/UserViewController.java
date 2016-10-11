package com.aristotle.admin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserViewController {

    @RequestMapping(value = {"/home", "/index.html"})
    public ModelAndView userHome(ModelAndView mv, HttpServletRequest request) {
        mv.getModel().put("fragmentName", "test2");
        mv.getModel().put("fragmentFile", "common/test");

        mv.setViewName("user_template");
        return mv;
    }

    @RequestMapping(value = {"/me/editProfile", "/me/editProfile.html"})
    public ModelAndView userEditProfile(ModelAndView mv, HttpServletRequest request) {
        mv.getModel().put("fragmentName", "edit_profile");
        mv.getModel().put("fragmentFile", "user/edit_profile");

        mv.setViewName("user_template");
        return mv;
    }

    @RequestMapping(value = {"/me/donations", "/me/donations.html"})
    public ModelAndView userDonations(ModelAndView mv, HttpServletRequest request) {
        mv.getModel().put("fragmentName", "my_donations");
        mv.getModel().put("fragmentFile", "user/my_donations");

        mv.setViewName("user_template");
        return mv;
    }

    @RequestMapping(value = {"/me/awaz", "/me/awaz.html"})
    public ModelAndView userAwaz(ModelAndView mv, HttpServletRequest request) {
        mv.getModel().put("fragmentName", "awaz");
        mv.getModel().put("fragmentFile", "user/awaz");

        mv.setViewName("user_template");
        return mv;
    }
}
