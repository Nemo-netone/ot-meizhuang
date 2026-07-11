package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.dao.ConsultingInfoDao;
import com.example.entity.Account;
import com.example.entity.ConsultingInfo;
import com.example.entity.GoodsInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ConsultingInfoService {

    @Resource
    private ConsultingInfoDao consultingInfoDao;
    @Resource
    private GoodsInfoService goodsInfoService;
	@Resource
	private AdminInfoService adminInfoService;
	@Resource
	private UserInfoService userInfoService;


    public ConsultingInfo add(ConsultingInfo consultingInfo) {
        consultingInfo.setCreateTime(DateUtil.formatDateTime(new Date()));
        String content = consultingInfo.getContent();
        if (content.length() > 255) {
            consultingInfo.setContent(content.substring(0, 250));
        }
        consultingInfoDao.insertSelective(consultingInfo);
        return consultingInfo;
    }

    public void delete(Long id) {
        consultingInfoDao.deleteByPrimaryKey(id);
    }

    public void update(ConsultingInfo consultingInfo) {
//        String content = consultingInfo.getContent();
//        if (content.length() > 255) {
//            consultingInfo.setContent(content.substring(0, 250));
//        }
        consultingInfoDao.updateByPrimaryKeySelective(consultingInfo);
    }

    public ConsultingInfo findById(Long id) {
        return consultingInfoDao.selectByPrimaryKey(id);
    }

    public List<ConsultingInfo> findAll() {
        return consultingInfoDao.selectAll();
    }

    public List<ConsultingInfo> findAll(Long goodsId) {
        List<ConsultingInfo> list = consultingInfoDao.findByGoodsId(goodsId);
        if (!CollectionUtil.isEmpty(list)) {
            for (ConsultingInfo info : list) {
                Long userId = info.getUserId();
                Integer level = info.getLevel();
				if (level == 1) {
					info.setUserName(adminInfoService.findById(userId).getName());
				}
				if (level == 3) {
					info.setUserName(userInfoService.findById(userId).getName());
				}

            }
        }
        return list;
    }

    public PageInfo<ConsultingInfo> findPage(Integer pageNum, Integer pageSize, String name, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        Account user = (Account)request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "用户未登录");
        }
        List<ConsultingInfo> all;
        if (user.getLevel() == 1) {
            all = consultingInfoDao.selectAll();
        } else {
            all = consultingInfoDao.findByContent(name, user.getId(), user.getLevel());
        }
        for (ConsultingInfo info : all) {
            Long userId = info.getUserId();
            Integer level = info.getLevel();
            GoodsInfo goodsInfo = goodsInfoService.findById(info.getGoodsId());
            info.setGoodsName(goodsInfo.getName());
				if (level == 1) {
					info.setUserName(adminInfoService.findById(userId).getName());
				}
				if (level == 3) {
					info.setUserName(userInfoService.findById(userId).getName());
				}

        }

        return PageInfo.of(all);
    }

}
