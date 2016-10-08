package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.LocationRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRoleRepository extends JpaRepository<LocationRole, Long> {

    public abstract LocationRole getLocationRoleByLocationIdAndRoleId(Long locationId, Long roleId);

}