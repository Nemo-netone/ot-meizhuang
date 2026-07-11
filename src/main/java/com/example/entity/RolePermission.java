package com.example.entity;

import javax.persistence.*;
public class RolePermission {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Id
    @Column(name = "model_id")
    private Integer modelId;

    @Id
    @Column(name = "operation_id")
    private Integer operationId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;
}
