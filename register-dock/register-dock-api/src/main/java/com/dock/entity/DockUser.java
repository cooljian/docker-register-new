package com.dock.entity;

import com.dock.core.entity.AbstractEntity;

import java.io.Serializable;

/**
 * Created by gaojian on 2016/9/7.
 */
public class DockUser extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -2981110590693434703L;

    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
