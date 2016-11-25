package com.dock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaojian on 2016/4/21.
 */
@Controller("helloWorldController")
public class HelloWorldController
{
    @RequestMapping(value = "/hello")
    public ModelAndView helloJava(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return new ModelAndView("/hello", "message", "xxxxxxxxxxxxsoso");
    }
}
