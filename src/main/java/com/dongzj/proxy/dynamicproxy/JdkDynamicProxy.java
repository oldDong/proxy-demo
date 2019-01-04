package com.dongzj.proxy.dynamicproxy;

import com.dongzj.proxy.service.IUserService;
import com.dongzj.proxy.service.impl.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * <p>
 * 原理：
 * class $jdkProxy implements IUserService{}
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/19
 * Time: 18:05
 */
public class JdkDynamicProxy {

    /**
     * 接收一个目标对象
     */
    private Object target;

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //获取当前执行的方法的方法名
                        String methodName = method.getName();
                        //方法返回值
                        Object result;
                        if ("find".equals(methodName)) {
                            //直接调用目标对象方法
                            result = method.invoke(target, args);
                        } else {
                            System.out.println("开启事务...");
                            //调用目标对象方法
                            result = method.invoke(target, args);
                            System.out.println("提交事务...");
                        }
                        return result;
                    }
                });
        return proxy;
    }

//    目标对象:class com.dongzj.proxy.service.impl.UserService
//    代理对象：class com.sun.proxy.$Proxy0
//    开启事务...
//    模拟：保存用户!
//    提交事务...
    public static void main(String[] args) {
        //创建目标对象
        IUserService target = new UserService();
        System.out.println("目标对象:" + target.getClass());
        //代理对象
        IUserService proxy = (IUserService) new JdkDynamicProxy(target).getProxyInstance();
        System.out.println("代理对象：" + proxy.getClass());
        //执行代理对象的方法
        proxy.save();
    }
}
