package com.dongzj.proxy.dynamicproxy;

import com.dongzj.proxy.service.IUserService;
import com.dongzj.proxy.service.impl.UserService;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * Cglib动态代理
 * <p>
 * 原理
 * class cglibProxy extends UserService{}
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/20
 * Time: 11:28
 */
public class CglibDynamicProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(Object target) {
        //给业务对象赋值
        this.target = target;
        //创建加强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        //加强器指定要代理的业务类
        enhancer.setSuperclass(this.target.getClass());
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而CallBack则需要实现intercept()方法
        enhancer.setCallback(this);
        //创建动态代理类对象并返回
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result;
        if ("find".equals(method.getName())) {
            //直接调用目标对象方法
            result = method.invoke(o, objects);
        } else {
            System.out.println("开启事务...");
            //调用目标对象方法
            result = methodProxy.invokeSuper(o, objects);
            System.out.println("提交事务...");
        }
        return result;
    }

    //    目标对象:class com.dongzj.proxy.service.impl.UserService
    //    代理对象：class com.dongzj.proxy.service.impl.UserService$$EnhancerByCGLIB$$f97d105a
    //    开启事务...
    //    模拟：保存用户!
    //    提交事务...
    public static void main(String[] args) {
        //创建目标对象
        IUserService target = new UserService();
        System.out.println("目标对象:" + target.getClass());
        //代理对象
        IUserService proxy = (IUserService) new CglibDynamicProxy().getInstance(target);
        System.out.println("代理对象：" + proxy.getClass());
        //执行代理对象的方法
        proxy.save();
    }
}
