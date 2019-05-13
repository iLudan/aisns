package com.aiaaa.web.controller;

import io.swagger.annotations.Api;
import org.junit.runner.RunWith;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/a")
public class IndexController {

    private static Logger logger =  LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = {"/a","/index"})
    public String index(Model model){
        logger.info("this is index");
        model.addAttribute("name","dandan");
        return "index";
    }

}
