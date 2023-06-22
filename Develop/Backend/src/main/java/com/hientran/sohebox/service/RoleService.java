package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.RoleTbl;
import com.hientran.sohebox.repository.RoleRepository;
import com.hientran.sohebox.vo.RoleVO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public RoleTbl getByRoleName(String roleName) {
		return roleRepository.findFirstByRoleName(roleName);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<RoleTbl> create(List<RoleVO> voList) {
		List<RoleTbl> result = new ArrayList<RoleTbl>();
		for (RoleVO vo : voList) {
			RoleTbl roleUpdate = roleRepository.findFirstByRoleName(vo.getRoleName());
			if (ObjectUtils.isEmpty(roleUpdate)) {
				RoleTbl roleInsert = new RoleTbl();
				roleInsert.setRoleName(vo.getRoleName());
				roleInsert.setDescription(vo.getDescription());
				result.add(roleRepository.save(roleInsert));
			} else {
				roleUpdate.setDescription(vo.getDescription());
				result.add(roleRepository.save(roleUpdate));
			}

		}
		return result;
	}
}