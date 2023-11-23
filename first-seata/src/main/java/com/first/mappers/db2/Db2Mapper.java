package com.first.mappers.db2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @description: 价钱
 * @author: cuiweiman
 * @date: 2023/8/16 15:20
 */
@Mapper
public interface Db2Mapper {

    /**
     * coursedb_1.course_1 的 1686947115286138881 记录 中 status 增加 100
     *
     * @return res
     */
    @Update("update course_1 set c_status=c_status+100 where cid = 1686947115286138881")
    int bbbAdd();

}
