package com.hientran.sohebox.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.UserTbl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService extends TransformerService {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private UserDetailsServiceImpl userService;

	/**
	 *
	 * Check data authentication
	 *
	 */
	protected APIResponse<Object> isDataAuthentication(long userID) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Get current logged user
		UserTbl loggedUser = userService.getCurrentLoginUser();

		// Check authentication if not role creator
		if (!StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_CREATOR)) {

			// Just return data of logged user
			if (userID != loggedUser.getId()) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
			}
		}

		// Return
		return result;

	}
}
