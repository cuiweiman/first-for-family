package proxy;

/**
 * CGLib 代理不需要 有接口
 *
 * @description: 有耳非文
 * @author: cuiweiman
 * @date: 2024/4/16 18:59
 */
public class YouErFeiWen {

    public String sayHi(String name) {
        System.out.println("Hi " + name);
        return "结束";
    }

}
