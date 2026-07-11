package com.example.dao;


import com.example.entity.ConsultingInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ConsultingInfoDao extends Mapper<ConsultingInfo> {

    List<ConsultingInfo> findByContent(@Param("name") String name,
                                    @Param("userId") Long userId,
                                    @Param("level") Integer level);

    List<ConsultingInfo> findByGoodsIdAndUserId(@Param("goodsId") Long goodsId,
                                             @Param("userId") Long userId,
                                             @Param("level") Integer level);

    List<ConsultingInfo> findByGoodsId(@Param("goodsId") Long goodsId);

    @Select("select count(id) from comment_info")
    Integer count();
}