package com.first.shardingsphere.base.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 查询 订单号 总订单金额
 * @author: cuiweiman
 * @date: 2023/8/3 17:05
 */
@Data
public class OrderPO {

    private String orderNo;

    private BigDecimal totalAmount;

}
