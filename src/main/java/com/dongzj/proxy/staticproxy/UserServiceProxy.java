package com.dongzj.proxy.staticproxy;

import com.dongzj.proxy.service.IUserService;
import com.dongzj.proxy.service.impl.UserService;

/**
 * 静态代理
 * 特点：
 * 1、目标对象必须要实现接口
 * 2、代理对象，要实现与目标对象一样的接口
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/19
 * Time: 18:01
 */
public class UserServiceProxy implements IUserService {

    private IUserService target = new UserService();

    public void save() {
        System.out.println("代理操作：开启事务...");
        target.save();
        System.out.println("代理操作：提交事务...");
    }

    public void find() {
        target.find();
    }

    public static void main(String[] args) {
        //代理对象
        IUserService proxy = new UserServiceProxy();
        //执行代理方法
        proxy.save();
    }
}
