package com.dock.service.impl;

import com.dock.data.mybatis.service.impl.AbstractIbatisServiceImpl;
import com.dock.entity.UserEntity;
import com.dock.dao.UserDao;
import com.dock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaojian on 2016/11/16.
 */
@Service("userService")
public class UserServiceImpl extends AbstractIbatisServiceImpl<UserEntity, String> implements UserService {
    @Autowired
    private  UserDao userDao;
    @Override
    protected UserDao getAbstractIBatisDao() {
        return this.userDao;
    }
}
