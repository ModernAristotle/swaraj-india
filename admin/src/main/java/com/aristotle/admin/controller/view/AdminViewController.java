package com.aristotle.admin.controller.view;

import com.aristotle.admin.controller.beans.UserPermissionBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminViewController {


    @RequestMapping(value = {"/admin/{submenu}/{page}.html"})
    public ModelAndView generic(ModelAndView mv, HttpServletRequest request, @PathVariable("submenu") String subMenu, @PathVariable("page") String page) {
        System.out.println("Submenu : " + subMenu);
        System.out.println("page : " + page);
        return getModelAndView(mv, page, "admin/" + subMenu + "/" + page);
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
