package proxy.jdkproxy;

import proxy.LiuDeHua;
import proxy.Star;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @description: JDK 动态代理 增强
 * @author: cuiweiman
 * @date: 2024/4/16 17:27
 */
public class JdkProxy implements InvocationHandler {

    private final Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                this.target.getClass().getClassLoader(),// 类加载器
                this.target.getClass().getInterfaces(),  // 代理接口
                this); // 调用处理器
    }

    /**
     * debug 断点处暂停时，IDEA 会调用被代理类的 toString() 方法获取 相关信息，鼠标悬停显示的好像就是那个东西。
     * 由于代理类代理该类的所有方法（包括toString），因此暂停一次就会输出一次『调用了toString()方法』的相关信息
     * <p>
     * 多数情况下调用一下toString()方法没有什么问题，但是也有例外，
     * 比如重写了 toString()方法的类，随意的调用 toString()方法会导致未知的问题，
     * 比如 Dubbo 的 AbstractConfig 类，对这个类的 debug 会导致其子类 ReferenceConfig 的 initialized 属性错误的被修改为true，
     * 进而无法正确的生成Dubbo代理。
     * <p>
     * 另外，IDEA 在 debug 时调用 toString() 方法的情况是可以在配置中关掉的，配置位置是
     * Settings->Build,Execution,Development->Debugger->Java->Enable 'toString()' object view
     *
     * @param proxyClass 代理的对象,
     * @param method     被代理类的代理方法,
     * @param args       方法参数
     * @return 被代理方法的执行结果
     * @throws Throwable 错误信息
     */
    @Override
    public Object invoke(Object proxyClass, Method method, Object[] args) throws Throwable {
        // 被代理对象 方法增强
        System.out.println("[代理开始]  Method: " + method.getName() + ", Args: " + Arrays.toString(args));
        if (proxyClass instanceof JdkProxy) {
            System.out.println("------ JdkProxy");
        } else if (proxyClass instanceof LiuDeHua) {
            System.out.println("------ LiuDeHua");
        } else if (proxyClass instanceof Star star) {
            System.out.println("------ Star  说明【 Object proxyClass 是被代理对象】" + Arrays.toString(star.getClass().getConstructors()));
        }
        // 执行 被代理方法
        Object invoke = method.invoke(this.target, args);
        System.out.println("[代理结束]");
        return invoke;
    }


}
