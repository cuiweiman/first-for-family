package com.first.mappers.db1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @description: 减钱
 * @author: cuiweiman
 * @date: 2023/8/16 15:20
 */
@Mapper
public interface Db1Mapper {

    /**
     * coursedb_0.course_0 的 1686947115428745218 记录 中 status 减少 100
     *
     * @return res
     */
    @Update("update course_0 set c_status=c_status-100 where cid = 1686947115428745218")
    int aaaReduce();


}
