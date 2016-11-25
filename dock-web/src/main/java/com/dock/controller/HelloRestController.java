package com.dock.controller;

import com.dock.core.response.ResponseResult;
import com.dock.service.ConnectAndHandleDBService;
import com.dock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by gaojian on 2016/4/22.
 */
@RestController("helloRestController")
public class HelloRestController
{
    @Autowired
    private ConnectAndHandleDBService connectAndHandleDBService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hellorest/{name}", method = RequestMethod.GET)
    public ResponseResult helloRest(@PathVariable("name") String name) throws SQLException, IOException, ClassNotFoundException
    {
        userService.get("00042a540b1a46c49dc317768d474f1e");
        return ResponseResult.ok();
    }
}
