package com.dock.facade;

import com.dock.service.UserService;
import com.dock.entity.DockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

/**
 * Created by gaojian on 2016/9/7.
 */
@RestController
public class UserFacade {
    @Autowired
    private UserService userService;

    @GetMapping
    public DockUser test(@QueryParam("id") String id){
        return userService.getById(id);
    }

}
