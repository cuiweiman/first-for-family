package concurrency.disruptor.common;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * 队列消费者
 *
 * @description: 订单消息处理
 * @author: cuiweiman
 * @date: 2023/12/25 16:08
 */
public class OrderHandler implements EventHandler<Order>, WorkHandler<Order> {

    private final String consumerId;

    public OrderHandler(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order order, long sequence, boolean endOfBatch) {
        System.out.printf("OrderHandler: %s, 消费信息: %s, sequence: %d, 最后一个元素: %b %n",
                this.consumerId, order.toString(), sequence, endOfBatch);
    }

    @Override
    public void onEvent(Order order) {
        System.out.printf("WorkHandler<Order>:  消费者ID: %s, 消息内容: %s%n", this.consumerId, order.toString());
    }
}
