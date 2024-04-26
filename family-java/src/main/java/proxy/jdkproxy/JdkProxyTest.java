package proxy.jdkproxy;

import proxy.LiuDeHua;
import proxy.Star;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/4/16 17:32
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        JdkProxy invocationHandler = new JdkProxy(new LiuDeHua());
        Star proxiedObj = (Star) invocationHandler.getProxyInstance();
        String jackson = proxiedObj.sayHi("刘德华");
        System.out.println("return " + jackson);
    }

}
