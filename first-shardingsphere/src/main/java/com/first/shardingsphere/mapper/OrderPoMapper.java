package com.first.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.first.shardingsphere.base.po.OrderPO;

import java.util.List;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/3 17:06
 */
public interface OrderPoMapper extends BaseMapper<OrderPO> {

    /**
     * 查询 订单号 与 订单总金额
     *
     * @return OrderPO
     */
    List<OrderPO> getOrderAmount();

}
