package concurrency.disruptor.common;

import com.lmax.disruptor.EventFactory;

/**
 * 队列元素 对象创建工厂
 *
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/25 16:07
 */
public class OrderFactory implements EventFactory<Order> {

    @Override
    public Order newInstance() {
        return new Order();
    }

}
