package com.dock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by gaojian on 2016/11/17.
 */
@Controller("loginController")
public class LoginController {

    @RequestMapping("/tologin")
    public ModelAndView tologin(){
        return new ModelAndView("/login", "", "");
    }
}
