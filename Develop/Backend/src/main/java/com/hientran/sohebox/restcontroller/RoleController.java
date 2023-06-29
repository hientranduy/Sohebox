package com.hientran.sohebox.restcontroller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.response.ResponseWithBody;
import com.hientran.sohebox.entity.RoleTbl;
import com.hientran.sohebox.service.RoleService;
import com.hientran.sohebox.utils.ResponseUtil;
import com.hientran.sohebox.vo.RoleVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@RestController
@RequiredArgsConstructor
public class RoleController extends BaseRestController {

	private final RoleService roleService;

	@PostMapping(ApiPublicConstants.API_ROLE + ApiPublicConstants.ADD)
	public ResponseEntity<?> create(@Validated @RequestBody List<RoleVO> voList) {
		List<RoleTbl> roleTbls = roleService.create(voList);

		// Return
		if (CollectionUtils.isEmpty(roleTbls)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			ResponseWithBody<List<RoleTbl>> result = new ResponseWithBody<>();
			result.setBody(roleTbls);
			result.setStatus(ResponseUtil.createSuccessStatus());
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
