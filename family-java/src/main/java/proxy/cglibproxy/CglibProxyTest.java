package proxy.cglibproxy;

import proxy.YouErFeiWen;

/**
 * 直接运行会报错  Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @2ef1e4fa
 * 是因为 JDK9+ 对 反射进行了限制。
 * 可以通过 VM Option: --add-opens java.base/java.lang=ALL-UNNAMED 来兼容
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/4/16 18:34
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        YouErFeiWen proxiedObj = (YouErFeiWen) new CglibProxy().getProxyInstance(YouErFeiWen.class);

        String jackson = proxiedObj.sayHi("有耳非文");
        System.out.println("return " + jackson);
    }

}
