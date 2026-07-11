package com.example.controller;

import com.example.common.Result;
import com.example.entity.CustomerlnquiryInfo;
import com.example.service.CustomerlnquiryInfoService;
import com.example.vo.CustomerlnquiryInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/customerlnquiryInfo")
public class CustomerlnquiryInfoController {
    @Resource
    private CustomerlnquiryInfoService customerlnquiryInfoService;

    @PostMapping
    public Result<CustomerlnquiryInfo> add(@RequestBody CustomerlnquiryInfoVo customerlnquiryInfo) {
        customerlnquiryInfoService.add(customerlnquiryInfo);
        return Result.success(customerlnquiryInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        customerlnquiryInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody CustomerlnquiryInfoVo customerlnquiryInfo) {
        customerlnquiryInfoService.update(customerlnquiryInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<CustomerlnquiryInfo> detail(@PathVariable Long id) {
        CustomerlnquiryInfo customerlnquiryInfo = customerlnquiryInfoService.findById(id);
        return Result.success(customerlnquiryInfo);
    }

    @GetMapping
    public Result<List<CustomerlnquiryInfoVo>> all() {
        return Result.success(customerlnquiryInfoService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<List<CustomerlnquiryInfoVo>> page(@PathVariable String name,
                                                HttpServletRequest request) {
        return Result.success(customerlnquiryInfoService.findAllPage(request,name));
    }

}
