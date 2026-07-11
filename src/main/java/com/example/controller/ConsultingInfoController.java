package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.ConsultingInfo;
import com.example.entity.OrderInfo;
import com.example.service.ConsultingInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/consultingInfo")
public class ConsultingInfoController {
    @Resource
    private ConsultingInfoService consultingInfoService;

    @PostMapping
    public Result<ConsultingInfo> add(@RequestBody ConsultingInfo consultingInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        consultingInfo.setUserId(user.getId());
        consultingInfoService.add(consultingInfo);
        return Result.success(consultingInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        consultingInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ConsultingInfo consultingInfo) {
        consultingInfoService.update(consultingInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<ConsultingInfo> detail(@PathVariable Long id) {
        ConsultingInfo consultingInfo = consultingInfoService.findById(id);
        return Result.success(consultingInfo);
    }

    /// consultingInfoComment/findByForeignId
    @GetMapping
    public Result<List<ConsultingInfo>> all() {
        return Result.success(consultingInfoService.findAll());
    }

    @GetMapping("/all/{goodsId}")
    public Result<List<ConsultingInfo>> all(@PathVariable("goodsId") Long goodsId) {
        return Result.success(consultingInfoService.findAll(goodsId));
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<ConsultingInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @PathVariable String name,
                                              HttpServletRequest request) {
        return Result.success(consultingInfoService.findPage(pageNum, pageSize, name, request));
    }
}
