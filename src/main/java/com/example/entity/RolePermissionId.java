package com.example.entity;

import java.io.Serializable;

// 复合主键类
public class RolePermissionId implements Serializable {
    private Integer roleId;
    private Integer modelId;
    private Integer operationId;
}