AOP

    1、AOP是基于动态代理模式
    
    2、AOP是方法级别的（要测试的方法不能为static修饰，因为接口中不能存在静态方法，编译就会报错）
    
    3、AOP可以分离业务代码和关注点代码（重复代码），在执行业务代码时，动态的注入关注点代码。切面就是关注点代码形成的类。
    
Spring AOP

    DefaultAopProxyFactory
    
    public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
        public DefaultAopProxyFactory() {
        }
    
        public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
            if (!config.isOptimize() && !config.isProxyTargetClass() && !this.hasNoUserSuppliedProxyInterfaces(config)) {
                return new JdkDynamicAopProxy(config);
            } else {
                Class<?> targetClass = config.getTargetClass();
                if (targetClass == null) {
                    throw new AopConfigException("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
                } else {
                    return (AopProxy)(!targetClass.isInterface() && !Proxy.isProxyClass(targetClass) ? new ObjenesisCglibAopProxy(config) : new JdkDynamicAopProxy(config));
                }
            }
        }
    
        private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
            Class<?>[] ifcs = config.getProxiedInterfaces();
            return ifcs.length == 0 || ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0]);
        }
    }