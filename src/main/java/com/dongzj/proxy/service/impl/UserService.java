package com.dongzj.proxy.service.impl;

import com.dongzj.proxy.service.IUserService;

/**
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/19
 * Time: 17:59
 */
public class UserService implements IUserService {

    public void save() {
        System.out.println("模拟：保存用户!");
    }

    public void find() {
        System.out.println("模拟：查询用户！");
    }
}
