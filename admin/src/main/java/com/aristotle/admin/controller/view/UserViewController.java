package com.aristotle.admin.controller.view;

import com.aristotle.admin.controller.beans.UserPermissionBean;
import com.aristotle.admin.controller.beans.UserSessionObject;
import com.aristotle.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserViewController {

    @Autowired
    private LoginService loginService;
    private UserPermissionBean emptyUserPermissionBean = new UserPermissionBean();

    @RequestMapping(value = {"/test"})
    public ModelAndView test(ModelAndView mv, HttpServletRequest request) {
        UserPermissionBean userPermissionBean = new UserPermissionBean();
        userPermissionBean.setSuperAdmin(true);
        mv.getModel().put("userPermission", userPermissionBean);
        mv.setViewName("ajs_template");
        return mv;
    }

    @RequestMapping(value = {"/me/{fragmentName}.html"})
    public String genericEachHtmlView(ModelAndView mv, HttpServletRequest request, @PathVariable("fragmentName") String fragmentName) {
        return "user/" + fragmentName;
    }
//    @RequestMapping(value = {"/me/basic.html"})
//    public String basic(ModelAndView mv, HttpServletRequest request) {
//        return "user/basic";
//    }
//
//    @RequestMapping(value = {"/home", "/index.html"})
//    public ModelAndView userHome(ModelAndView mv, HttpServletRequest request) {
//        mv = getModelAndView(mv, request, "home", "user/home");
//        return mv;
//    }
//
//    @RequestMapping(value = {"/me/editProfile", "/me/editProfile.html"})
//    public ModelAndView userEditProfile(ModelAndView mv, HttpServletRequest request) {
//        mv = getModelAndView(mv, request, "edit_profile", "user/edit_profile");
//        return mv;
//    }
//
//    @RequestMapping(value = {"/me/donations", "/me/donations.html"})
//    public ModelAndView userDonations(ModelAndView mv, HttpServletRequest request) {
//        mv = getModelAndView(mv, request, "my_donations", "user/my_donations");
//        return mv;
//    }
//
//    @RequestMapping(value = {"/me/awaz  ", "/me/awaz.html"})
//    public ModelAndView userAwaz(ModelAndView mv, HttpServletRequest request) {
//        mv = getModelAndView(mv, request, "awaz", "user/awaz");
//        return mv;
//    }
//
//    @RequestMapping(value = {"/me/{fragmentName}.html"})
//    public ModelAndView genericView(ModelAndView mv, HttpServletRequest request, @PathVariable("fragmentName") String fragmentName) {
//        mv = getModelAndView(mv, request, fragmentName, "user/" + fragmentName);
//        return mv;
//    }


    private ModelAndView getModelAndView(ModelAndView mv, HttpServletRequest request, String fragmentName, String fragmentFile) {
        mv.getModel().put("fragmentName", fragmentName);
        mv.getModel().put("fragmentFile", fragmentFile);
        UserSessionObject userSessionObject = loginService.getUserSessionObject(request);
        if (userSessionObject == null) {
            mv.getModel().put("userPermission", emptyUserPermissionBean);

        } else {
            UserPermissionBean userPermissionBean = userSessionObject.getUserPermissionBean();
            mv.getModel().put("userPermission", userPermissionBean);
        }

        mv.setViewName("user_template");
        return mv;
    }
}
