package com.example.dao;

import com.example.entity.SystemInfo;
import com.example.vo.SystemInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SystemInfoDao extends Mapper<SystemInfo> {
    List<SystemInfoVo> findByName(@Param("name") String name);



}