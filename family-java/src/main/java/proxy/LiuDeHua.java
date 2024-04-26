package proxy;

/**
 * @description: 刘德华
 * @author: cuiweiman
 * @date: 2024/4/16 17:01
 */
public class LiuDeHua implements Star {

    @Override
    public String sayHi(String name) {
        System.out.println("Hi " + name);
        return "结束";
    }

}
