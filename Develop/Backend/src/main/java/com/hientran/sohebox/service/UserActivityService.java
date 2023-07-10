package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.UserActivityVO;
import com.hientran.sohebox.dto.UserVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.UserActivityTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.UserActivityRepository;
import com.hientran.sohebox.repository.UserRepository;
import com.hientran.sohebox.specification.UserActivitySpecs.UserActivityTblEnum;
import com.hientran.sohebox.transformer.UserActivityTransformer;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

	private final UserActivityRepository userActivityRepository;
	private final UserActivityTransformer userActivityTransformer;
	private final UserRepository userRepository;
	private final TypeCache typeCache;

	/**
	 *
	 * Create
	 *
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(UserActivityVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// User must not null
			if (vo.getUser() == null && vo.getUserTbl() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserActivityTblEnum.user.name()));
			}

			// Activity must not null
			if (vo.getActivity() == null) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserActivityTblEnum.activity.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Create activity
		if (result.getStatus() == null) {
			// Transform
			UserActivityTbl tbl = userActivityTransformer.convertToTbl(vo);

			// Set user
			if (vo.getUserTbl() != null) {
				tbl.setUser(vo.getUserTbl());
			} else {
				tbl.setUser(userRepository.findById(vo.getUser().getId()).get());
			}

			// Set activity
			tbl.setActivity(typeCache.getType(vo.getActivity().getTypeClass(), vo.getActivity().getTypeCode()));

			// Create User
			tbl = userActivityRepository.save(tbl);

			// Set id return
			result.setData(tbl.getId());
		}

		// Return
		return result;
	}

	/**
	 * Write user activity
	 *
	 * @param userTbl
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void recordUserActivity(UserTbl userTbl, String activity) {
		UserActivityTbl tbl = new UserActivityTbl();

		// Set user
		tbl.setUser(userTbl);

		// Set activity
		tbl.setActivity(typeCache.getType(DBConstants.TYPE_CLASS_USER_ACTIVITY, activity));

		// Create User
		tbl = userActivityRepository.save(tbl);
	}

	/**
	 * Write user activity
	 *
	 * @param userVO
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void recordUserActivity(UserVO userVO, String activity) {
		UserActivityTbl tbl = new UserActivityTbl();

		// Set user
		tbl.setUser(userRepository.findById(userVO.getId()).get());

		// Set activity
		tbl.setActivity(typeCache.getType(DBConstants.TYPE_CLASS_USER_ACTIVITY, activity));

		// Create User
		tbl = userActivityRepository.save(tbl);
	}
}
