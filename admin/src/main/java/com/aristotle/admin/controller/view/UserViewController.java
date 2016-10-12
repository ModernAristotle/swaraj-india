package com.aristotle.admin.controller.view;

import com.aristotle.admin.controller.beans.UserPermissionBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserViewController {

    @RequestMapping(value = {"/home", "/index.html"})
    public ModelAndView userHome(ModelAndView mv, HttpServletRequest request) {
        mv = getModelAndView(mv, "home", "user/home");
        return mv;
    }

    @RequestMapping(value = {"/me/editProfile", "/me/editProfile.html"})
    public ModelAndView userEditProfile(ModelAndView mv, HttpServletRequest request) {
        mv = getModelAndView(mv, "edit_profile", "user/edit_profile");
        return mv;
    }

    @RequestMapping(value = {"/me/donations", "/me/donations.html"})
    public ModelAndView userDonations(ModelAndView mv, HttpServletRequest request) {
        mv = getModelAndView(mv, "my_donations", "user/my_donations");
        return mv;
    }

    @RequestMapping(value = {"/me/awaz  ", "/me/awaz.html"})
    public ModelAndView userAwaz(ModelAndView mv, HttpServletRequest request) {
        mv = getModelAndView(mv, "awaz", "user/awaz");
        return mv;
    }

    private ModelAndView getModelAndView(ModelAndView mv, String fragmentName, String fragmentFile) {
        mv.getModel().put("fragmentName", fragmentName);
        mv.getModel().put("fragmentFile", fragmentFile);
        UserPermissionBean userPermissionBean = new UserPermissionBean();
        userPermissionBean.setSuperAdmin(true);
        mv.getModel().put("userPermission", userPermissionBean);

        mv.setViewName("user_template");
        return mv;
    }
}
