package com.next.aristotle.web.controller;

import com.next.aristotle.web.service.HomeControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
public class HomeController {

    @Autowired
    private HomeControllerService homeControllerService;

    @RequestMapping(value = {"/index.html", "/"})
    public ModelAndView genericEachHtmlView(ModelAndView mv, HttpServletRequest request) throws Exception {
        mv.setViewName("pages/index");
        homeControllerService.generateHomePageModel(mv);
        mv.getModelMap().addAttribute("s3_static_content_dir", "");
        return mv;
    }

}
