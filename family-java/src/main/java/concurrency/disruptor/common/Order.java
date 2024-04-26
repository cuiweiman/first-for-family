package concurrency.disruptor.common;

/**
 * 队列元素 对象
 *
 * @description: 订单
 * @author: cuiweiman
 * @date: 2023/12/25 16:05
 */
public class Order {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                '}';
    }
}