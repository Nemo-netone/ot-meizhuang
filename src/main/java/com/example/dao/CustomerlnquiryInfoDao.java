package com.example.dao;

import com.example.entity.CustomerlnquiryInfo;
import com.example.vo.CustomerlnquiryInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CustomerlnquiryInfoDao extends Mapper<CustomerlnquiryInfo> {
    List<CustomerlnquiryInfoVo> findByName(@Param("name") String name);
    List<CustomerlnquiryInfoVo> findByNameParentId(@Param("name") String name);

    List<CustomerlnquiryInfoVo> findByParentId(Long parentId);
}
