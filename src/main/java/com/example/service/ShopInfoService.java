package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.example.dao.ShopInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.ShopInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.ShopInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ShopInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private ShopInfoDao shopInfoDao;

    public ShopInfo add(ShopInfo shopInfo) {
        List<Long> fileList = shopInfo.getFileList();
        if (!CollectionUtil.isEmpty(fileList)) {
            shopInfo.setFileIds(fileList.toString());
        }
        shopInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        shopInfoDao.insertSelective(shopInfo);
        return shopInfo;
    }

    public void delete(Long id) {
        shopInfoDao.deleteByPrimaryKey(id);
    }

    public void update(ShopInfo shopInfo) {
        List<Long> fileList = shopInfo.getFileList();
        if (!CollectionUtil.isEmpty(fileList)) {
            shopInfo.setFileIds(fileList.toString());
        }
        shopInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        shopInfoDao.updateByPrimaryKeySelective(shopInfo);
    }

    public ShopInfo findById(Long id) {
        return shopInfoDao.selectByPrimaryKey(id);
    }
    public ShopInfoVo findByLastId() {
        return shopInfoDao.findByLastId();
    }
    public List<ShopInfoVo> findAll() {
        return shopInfoDao.findByName("all");
    }

    public PageInfo<ShopInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShopInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<ShopInfoVo> findAllPage(HttpServletRequest request, String name) {
        return shopInfoDao.findByName(name);
    }

}
