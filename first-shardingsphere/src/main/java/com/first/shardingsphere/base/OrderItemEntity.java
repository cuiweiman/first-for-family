package com.first.shardingsphere.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: entity
 * @author: cuiweiman
 * @date: 2023/7/28 17:19
 */
@Data
@Builder
@TableName(value = "t_order_item")
public class OrderItemEntity {

    /**
     * ShardingSphere 配置分布式序列后，主键type必须为 AUTO
     * 由于不配置时默认是 ASSIGN_ID 雪花算法, 因此需要手动指定为 AUTO
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal price;
    private Integer number;

}
