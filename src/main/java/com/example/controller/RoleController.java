package com.example.controller;

import com.example.dao.AdminInfoDao;
import com.example.dao.PermissionUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    public ResponseEntity<List<AdminInfoDao.RoleDTO>> getAllRoles() {
        RoleController roleService = new RoleController();
        return ResponseEntity.ok(Objects.requireNonNull(roleService.getAllRoles().getBody()));
    }

    @PutMapping("/{roleId}/permissions")
    @PreAuthorize("hasAuthority('PERMISSION_ASSIGN')")
    public ResponseEntity<?> updatePermissions(
            @PathVariable Integer roleId,
            @RequestBody List<PermissionUpdateDTO> updates) {
        RoleController roleService = new RoleController();
        roleService.updatePermissions(roleId, updates);
        return ResponseEntity.ok().build();
    }
}