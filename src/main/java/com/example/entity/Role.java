package com.example.entity;

import javax.persistence.*;
import java.util.Set;

public class Role {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    private String name;
    private Integer level;

    @OneToMany(mappedBy = "role")
    private Set<RolePermission> permissions;
}
