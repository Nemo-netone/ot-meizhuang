package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.SystemInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.SystemInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.SystemInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SystemInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private SystemInfoDao systemInfoDao;

    public SystemInfo add(SystemInfo systemInfo) {
        systemInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        systemInfoDao.insertSelective(systemInfo);
        return systemInfo;
    }

    public void delete(Long id) {
        systemInfoDao.deleteByPrimaryKey(id);
    }

    public void update(SystemInfo systemInfo) {
        systemInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        systemInfoDao.updateByPrimaryKeySelective(systemInfo);
    }

    public SystemInfo findById(Long id) {
        return systemInfoDao.selectByPrimaryKey(id);
    }

    public List<SystemInfoVo> findAll() {
        return systemInfoDao.findByName("all");
    }

    public PageInfo<SystemInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<SystemInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<SystemInfoVo> findAllPage(HttpServletRequest request, String name) {
		return systemInfoDao.findByName(name);
    }

}
