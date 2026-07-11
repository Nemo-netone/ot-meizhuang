package com.example.service;

import com.example.dao.TroubleshootingDao;
import com.example.entity.Troubleshooting;
import com.example.vo.TroubleshootingVo;
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
public class TroubleshootingService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private TroubleshootingDao troubleshootingDao;

    public Troubleshooting add(Troubleshooting troubleshooting) {
        troubleshooting.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        troubleshootingDao.insertSelective(troubleshooting);
        return troubleshooting;
    }

    public void delete(Long id) {
        troubleshootingDao.deleteByPrimaryKey(id);
    }

    public void update(Troubleshooting troubleshooting) {
        troubleshooting.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        troubleshootingDao.updateByPrimaryKeySelective(troubleshooting);
    }

    public Troubleshooting findById(Long id) {
        return troubleshootingDao.selectByPrimaryKey(id);
    }

    public List<TroubleshootingVo> findAll() {
        return troubleshootingDao.findByName("all");
    }

    public PageInfo<TroubleshootingVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<TroubleshootingVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<TroubleshootingVo> findAllPage(HttpServletRequest request, String name) {
		return troubleshootingDao.findByName(name);
    }

}
