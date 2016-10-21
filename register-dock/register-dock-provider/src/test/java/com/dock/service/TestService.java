package com.dock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by gaojian on 2016/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:application.xml"})
public class TestService {
    @Autowired
    private UserService userService;

    @Test
    public void test(){
        userService.getById("xxxxxxxx");
    }

}
