package com.dock.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dock.service.ConnectAndHandleDBService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by gaojian on 2016/4/22.
 */
@RestController
public class HelloRestController
{
    @Autowired
    private ConnectAndHandleDBService connectAndHandleDBService;

    @RequestMapping(value = "/hellorest/{name}", method = RequestMethod.GET)
    public String helloRest(@PathVariable("name") String name) throws SQLException, IOException, ClassNotFoundException
    {
        List<Map<String, Object>> dataList = connectAndHandleDBService.handleData("select * from HEALTH_MONITOR_USER");
        return JSON.toJSONString(dataList.toString());
    }
}
