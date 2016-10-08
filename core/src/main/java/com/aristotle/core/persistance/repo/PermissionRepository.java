package com.aristotle.core.persistance.repo;

import com.aristotle.core.enums.AppPermission;
import com.aristotle.core.persistance.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    public Permission getPermissionByPermission(AppPermission name);

}
