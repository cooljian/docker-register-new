package com.dock.service;

import com.dock.entity.DockUser;

/**
 * Created by gaojian on 2016/9/7.
 */
public interface UserService {
    DockUser getById(String id);
}
