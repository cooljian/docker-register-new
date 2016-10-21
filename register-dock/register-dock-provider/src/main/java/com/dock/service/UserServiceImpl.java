package com.dock.service;

import com.dock.dao.UserDao;
import com.dock.entity.DockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaojian on 2016/9/7.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public DockUser getById(String id) {
        return (DockUser) userDao.getById(id);
    }
}
