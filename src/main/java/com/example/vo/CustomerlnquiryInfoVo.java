
package com.example.vo;

import com.example.entity.CustomerlnquiryInfo;

import java.util.List;

public class CustomerlnquiryInfoVo extends CustomerlnquiryInfo {
    private List<CustomerlnquiryInfoVo> children;

    public List<CustomerlnquiryInfoVo> getChildren() {
        return children;
    }

    public void setChildren(List<CustomerlnquiryInfoVo> children) {
        this.children = children;
    }
}
