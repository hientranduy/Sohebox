package com.hientran.sohebox.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.entity.UserActivityTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.UserActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

	private final UserActivityRepository userActivityRepository;
	private final TypeCache typeCache;

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
}
