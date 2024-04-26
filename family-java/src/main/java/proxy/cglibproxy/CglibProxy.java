package proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description: CGLib 动态代理
 * @author: cuiweiman
 * @date: 2024/4/16 18:30
 */
public class CglibProxy implements MethodInterceptor {

    public Object getProxyInstance(Class<?> clazz) {
        // 主要的增强类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * 增强类回调方法
     *
     * @param object      the enhanced object 增强对象，即
     * @param method      被拦截的方法
     * @param args        参数数组； 原始类型被包装
     * @param methodProxy 用于调用super（非拦截方法）； 可以根据需要多次调用
     * @return 代理方法的返回值。void 代理方法将忽略该值。
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // 被代理对象 方法增强
        System.out.println("[CGLib 代理开始]  Method: " + method.getName() + ", Args: " + Arrays.toString(args));

        /*
        错误形式 执行被代理方法。method 是被代理的方法，
        若按以下方式执行，又会被 CglibProxy 拦截，陷入递归，直至 栈溢出 StackOverflowError
        Object invoke1 = method.invoke(object, args);
        */

        // 执行 被代理方法
        Object invoke = methodProxy.invokeSuper(object, args);
        System.out.println("[CGLib 代理结束] " + "  " + invoke);
        return invoke;
    }

}
