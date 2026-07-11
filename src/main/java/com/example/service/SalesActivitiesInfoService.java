
package com.example.service;

import com.example.dao.SalesActivitiesInfoDao;
import com.example.entity.Account;
import com.example.entity.SalesActivitiesInfo;
import com.example.exception.CustomException;
import com.example.vo.SalesActivitiesInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SalesActivitiesInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private SalesActivitiesInfoDao salesActivitiesInfoDao;

    public SalesActivitiesInfo add(SalesActivitiesInfo salesActivitiesInfo) {
        salesActivitiesInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        salesActivitiesInfoDao.insertSelective(salesActivitiesInfo);
        return salesActivitiesInfo;
    }

    public void delete(Long id) {
        salesActivitiesInfoDao.deleteByPrimaryKey(id);
    }

    public void update(SalesActivitiesInfo salesActivitiesInfo) {
        salesActivitiesInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        salesActivitiesInfoDao.updateByPrimaryKeySelective(salesActivitiesInfo);
    }

    public SalesActivitiesInfo findById(Long id) {
        return salesActivitiesInfoDao.selectByPrimaryKey(id);
    }

    public List<SalesActivitiesInfoVo> findAll() {
        return salesActivitiesInfoDao.findByName("all");
    }

    public PageInfo<SalesActivitiesInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<SalesActivitiesInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<SalesActivitiesInfoVo> findAllPage(HttpServletRequest request, String name) {
        Account user = (Account)request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "用户未登录");
        }
        return salesActivitiesInfoDao.findByName(name);
    }

}
