package com.example.service;

import com.example.dao.CustomerlnquiryInfoDao;
import com.example.entity.Account;
import com.example.entity.CustomerlnquiryInfo;
import com.example.exception.CustomException;
import com.example.vo.CustomerlnquiryInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerlnquiryInfoService {

    @Resource
    private CustomerlnquiryInfoDao customerlnquiryInfoDao;

    public CustomerlnquiryInfo add(CustomerlnquiryInfo customerlnquiryInfo) {
        customerlnquiryInfoDao.insertSelective(customerlnquiryInfo);
        return customerlnquiryInfo;
    }

    public void delete(Long id) {
        customerlnquiryInfoDao.deleteByPrimaryKey(id);
    }

    public void update(CustomerlnquiryInfo customerlnquiryInfo) {
        customerlnquiryInfoDao.updateByPrimaryKeySelective(customerlnquiryInfo);
    }

    public CustomerlnquiryInfo findById(Long id) {
        return customerlnquiryInfoDao.selectByPrimaryKey(id);
    }

    public List<CustomerlnquiryInfoVo> findAll() {
        List<CustomerlnquiryInfoVo> all = customerlnquiryInfoDao.findByParentId(0L);
        for (CustomerlnquiryInfoVo customerlnquiryInfoVo : all) {
            Long id = customerlnquiryInfoVo.getId();
            List<CustomerlnquiryInfoVo> children = new ArrayList<>(customerlnquiryInfoDao.findByParentId(id));
            customerlnquiryInfoVo.setChildren(children);
        }
        return all;
    }

    public PageInfo<CustomerlnquiryInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<CustomerlnquiryInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<CustomerlnquiryInfoVo> findAllPage(HttpServletRequest request, String name) {
        Account user = (Account)request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "用户未登录");
        }
		return customerlnquiryInfoDao.findByNameParentId(user.getName());
    }

}
