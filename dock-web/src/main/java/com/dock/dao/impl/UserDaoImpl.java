package com.dock.dao.impl;

import com.dock.dao.UserDao;
import com.dock.data.mybatis.dao.impl.AbstractIBatisDaoImpl;
import com.dock.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by gaojian on 2016/11/16.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractIBatisDaoImpl<UserEntity, String> implements UserDao{

}
