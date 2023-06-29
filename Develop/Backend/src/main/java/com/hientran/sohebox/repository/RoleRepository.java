package com.hientran.sohebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.RoleTbl;

public interface RoleRepository extends JpaRepository<RoleTbl, Long>, JpaSpecificationExecutor<RoleTbl> {
	RoleTbl findFirstByRoleName(String roleName);
}
