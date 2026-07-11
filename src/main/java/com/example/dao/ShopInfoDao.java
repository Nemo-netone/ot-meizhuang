package com.example.dao;

import com.example.entity.ShopInfo;
import com.example.vo.ShopInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ShopInfoDao extends Mapper<ShopInfo> {
    List<ShopInfoVo> findByName(@Param("name") String name);
    ShopInfoVo findByLastId();
    
    
}
