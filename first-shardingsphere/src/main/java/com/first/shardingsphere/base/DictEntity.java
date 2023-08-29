package com.first.shardingsphere.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 广播表: 字典
 * @author: cuiweiman
 * @date: 2023/8/3 19:05
 */
@Data
@Builder
@TableName(value = "t_dict")
public class DictEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String dictType;

}
