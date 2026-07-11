
package com.example.dao;

import com.example.entity.AdvertiserInfo;
import com.example.entity.SalesActivitiesInfo;
import com.example.vo.AdvertiserInfoVo;
import com.example.vo.SalesActivitiesInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SalesActivitiesInfoDao extends Mapper<SalesActivitiesInfo> {
    List<SalesActivitiesInfoVo> findByName(@Param("name") String name);



}
