package com.hientran.sohebox.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.ResponseCode;
import com.hientran.sohebox.constants.enums.UserActivityTblEnum;
import com.hientran.sohebox.constants.enums.UserTblEnum;
import com.hientran.sohebox.entity.UserActivityTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.UserActivityRepository;
import com.hientran.sohebox.repository.UserRepository;
import com.hientran.sohebox.sco.SearchNumberVO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.sco.UserActivitySCO;
import com.hientran.sohebox.sco.UserSCO;
import com.hientran.sohebox.transformer.UserTransformer;
import com.hientran.sohebox.utils.MyDateUtils;
import com.hientran.sohebox.validation.UserValidation;
import com.hientran.sohebox.vo.ChangePasswordVO;
import com.hientran.sohebox.vo.ChangePrivateKeyVO;
import com.hientran.sohebox.vo.PageResultVO;
import com.hientran.sohebox.vo.UserStatusVO;
import com.hientran.sohebox.vo.UserVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	@PersistenceContext
	protected EntityManager entityManager;

	private final UserRepository userRepository;
	private final UserTransformer userTransformer;
	private final UserValidation validation;
	private final RoleService roleService;
	private final MdpService mdpService;
	private final UserActivityService userActivityService;
	private final UserActivityRepository userActivityRepository;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * 
	 * Create new User
	 * 
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(UserVO vo) {
		// Declare result
		APIResponse<Long> result = new APIResponse<Long>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// username must not null
			if (StringUtils.isBlank(vo.getUsername())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.username.name()));
			}

			// Password
			if (validation.isInvalidPassword(vo.getPassword())) {
				errors.add(ResponseCode.mapParam(ResponseCode.INVALID_FIELD, UserTblEnum.password.name()));
			}

			// First name must not null
			if (StringUtils.isBlank(vo.getFirstName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.firstName.name()));
			}

			// Last name must not null
			if (StringUtils.isBlank(vo.getLastName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.lastName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if username existed already
		if (result.getStatus() == null) {
			if (getByUserName(vo.getUsername()) != null) {
				result = new APIResponse<Long>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.EXISTED_USERNAME, vo.getUsername()));
			}
		}

		// Create User
		if (result.getStatus() == null) {
			// Transform
			UserTbl tbl = userTransformer.convertToTbl(vo);

			// Always set role "user"
			tbl.setRole(roleService.getByRoleName(DBConstants.USER_ROLE_USER));

			// Set mdp
			tbl.setMdp(mdpService.getMdp(vo.getPassword()));

			// Set avatar
			if (StringUtils.isBlank(vo.getAvatarUrl())) {
				tbl.setAvatarUrl(DBConstants.USER_DEFAULT_AVATAR);
			}

			// Private key must be null when create new account
			tbl.setPrivateKey(null);

			// Create User
			tbl = userRepository.save(tbl);
			result.setData(tbl.getId());
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Update logged User
	 *
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> update(UserVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Validate data
		if (result.getStatus() == null) {

			List<String> errors = new ArrayList<>();

			// username must not null
			if (StringUtils.isBlank(vo.getUsername())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.username.name()));
			}

			// First name must not null
			if (StringUtils.isBlank(vo.getFirstName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.firstName.name()));
			}

			// Last name must not null
			if (StringUtils.isBlank(vo.getLastName())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.lastName.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Object>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Update
		if (result.getStatus() == null) {
			UserTbl updateTbl = userDetailsServiceImpl.getCurrentLoginUser();
			updateTbl.setFirstName(vo.getFirstName());
			updateTbl.setLastName(vo.getLastName());
			userRepository.save(updateTbl);
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Change password
	 * 
	 * @param UserVO
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> changePassword(ChangePasswordVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		if (StringUtils.isNotBlank(vo.getUsername())) {
			//////////////////////////////////////////////
			// Change password case have input username //
			//////////////////////////////////////////////

			// Validate input
			if (result.getStatus() == null) {
				List<String> errors = new ArrayList<>();

				// Old password must not null
				if (StringUtils.isBlank(vo.getOldPassword())) {
					errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.password.name()));
				}

				// New Password
				if (validation.isInvalidPassword(vo.getNewPassword())) {
					errors.add(ResponseCode.mapParam(ResponseCode.INVALID_FIELD, UserTblEnum.password.name()));
				}

				// Record error
				if (CollectionUtils.isNotEmpty(errors)) {
					result = new APIResponse<Object>(HttpStatus.BAD_REQUEST, errors);
				}
			}

			// Change password
			if (result.getStatus() == null) {
				// Get user tbl
				UserTbl userTbl = userRepository.findFirstByUsername(vo.getUsername());

				// Check correct password
				if (userTbl != null && mdpService.isValidPassword(vo.getOldPassword(), userTbl.getMdp().getMdp())) {
					userTbl.setMdp(mdpService.getMdp(vo.getNewPassword()));
					userRepository.save(userTbl);

					// Record activity "CHANGE PASSWORD"
					userActivityService.recordUserActivity(userTbl, DBConstants.USER_ACTIVITY_CHANGE_PASSWORD);
				} else {
					result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_USER, null));
				}
			}
		} else {
			////////////////////////////////////////
			// Change password by logged username //
			////////////////////////////////////////
			// Validate input
			if (result.getStatus() == null) {
				List<String> errors = new ArrayList<>();

				// New Password
				if (validation.isInvalidPassword(vo.getNewPassword())) {
					errors.add(ResponseCode.mapParam(ResponseCode.INVALID_FIELD, UserTblEnum.password.name()));
				}

				// Record error
				if (CollectionUtils.isNotEmpty(errors)) {
					result = new APIResponse<Object>(HttpStatus.BAD_REQUEST, errors);
				}
			}

			// Change password
			if (result.getStatus() == null) {

				// Get User to update password
				UserTbl userTbl = userDetailsServiceImpl.getCurrentLoginUser();

				// Check correct password
				if (userTbl != null) {
					userTbl.setMdp(mdpService.getMdp(vo.getNewPassword()));
					userRepository.save(userTbl);

					// Record activity "CHANGE PASSWORD"
					userActivityService.recordUserActivity(userTbl,
							DBConstants.USER_ACTIVITY_CHANGE_PASSWORD_WHEN_LOGIN);
				} else {
					result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
							ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_USER, null));
				}
			}
		}

		// Return
		return result;
	}

	/**
	 * Delete current login User
	 *
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<?> deleteLoggedUser() {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get User
		UserTbl userTbl = userDetailsServiceImpl.getCurrentLoginUser();

		// Delete User
		if (userTbl != null) {
			userTbl.setDeleteFlag(DBConstants.USER_INACTIVE);
			userRepository.save(userTbl);

			// Record activity "DELETE ACCOUNT"
			userActivityService.recordUserActivity(userTbl, DBConstants.USER_ACTIVITY_DELETE);
		} else {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_USER, null));
		}

		// Return
		return result;
	}

	/**
	 * Get all Users
	 * 
	 * Only role creator
	 *
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchUserStatus(UserSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get All Users
		Page<UserTbl> page = userRepository.findAll(sco);

		// Transform data
		if (CollectionUtils.isNotEmpty(page.getContent())) {
			List<UserStatusVO> listElement = new ArrayList<UserStatusVO>();

			UserStatusVO userStatus;
			for (UserTbl tbl : page.getContent()) {
				userStatus = new UserStatusVO();
				userStatus.setId(tbl.getId());
				userStatus.setFirstName(tbl.getFirstName());
				userStatus.setLastName(tbl.getLastName());
				userStatus.setUsername(tbl.getUsername());
				userStatus.setRoleName(tbl.getRole().getRoleName());
				userStatus.setAvatarUrl(tbl.getAvatarUrl());

				// Get user activities
				SearchNumberVO userIdSearch = new SearchNumberVO();
				userIdSearch.setEq(tbl.getId().doubleValue());
				List<Sorter> sorters = new ArrayList<>();
				sorters.add(new Sorter(UserActivityTblEnum.createdDate.name(), DBConstants.DIRECTION_DECCENT));
				UserActivitySCO userActivitySCO = new UserActivitySCO();
				userActivitySCO.setUserId(userIdSearch);
				userActivitySCO.setSorters(sorters);
				userActivitySCO.setMaxRecordPerPage(1);

				Page<UserActivityTbl> userActivityList = userActivityRepository.findAll(userActivitySCO);
				long seconds = 0;
				if (CollectionUtils.isNotEmpty(userActivityList.getContent())) {
					UserActivityTbl userActivity = userActivityList.getContent().get(0);
					seconds = (new Date().getTime() - userActivity.getCreatedDate().getTime()) / 1000;

					userStatus.setStatus(userActivity.getActivity().getTypeName());
					userStatus.setDurationSeconds(seconds);
					userStatus.setDurationTime(MyDateUtils.formatDate(seconds));
				}
				listElement.add(userStatus);
			}

			// Sort data
			if (CollectionUtils.isEmpty(sco.getSorters())) {
				Collections.sort(listElement, new Comparator<UserStatusVO>() {
					@Override
					public int compare(UserStatusVO a, UserStatusVO b) {
						return (int) (a.getDurationSeconds() - b.getDurationSeconds());
					}
				});
			}

			// Set data return
			PageResultVO<UserStatusVO> data = new PageResultVO<UserStatusVO>();
			data.setElements(listElement);
			data.setTotalPage(page.getTotalPages());
			data.setTotalElement(page.getTotalElements());
			data.setCurrentPage(page.getPageable().getPageNumber());
			data.setPageSize(page.getPageable().getPageSize());

			result.setData(data);
		} else {

			// Set data return
			PageResultVO<UserStatusVO> data = new PageResultVO<UserStatusVO>();
			data.setElements(new ArrayList<UserStatusVO>());
			data.setTotalPage(0);
			data.setTotalElement(0);
			data.setCurrentPage(sco.getPageToGet());
			data.setPageSize(sco.getMaxRecordPerPage());

			result.setData(data);
		}

		// Return
		return result;
	}

	/**
	 * Get active user
	 * 
	 * Only role creator
	 *
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> searchActiveUser(UserSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get All Users
		List<Object[]> listData = userRepository.getActiveUser(sco, entityManager);

		// Transform data
		if (CollectionUtils.isNotEmpty(listData)) {
			List<UserStatusVO> listElement = new ArrayList<UserStatusVO>();

			UserStatusVO userStatus;
			for (Object[] object : listData) {
				// Get user
				BigInteger userId = (BigInteger) object[0];
				UserTbl userTbl = getTblById(userId.longValue());

				// Fill result item
				userStatus = new UserStatusVO();
				userStatus.setId(userTbl.getId());
				userStatus.setFirstName(userTbl.getFirstName());
				userStatus.setLastName(userTbl.getLastName());
				userStatus.setUsername(userTbl.getUsername());
				userStatus.setRoleName(userTbl.getRole().getRoleName());
				userStatus.setAvatarUrl(userTbl.getAvatarUrl());

				// Get user activities
				SearchNumberVO userIdSearch = new SearchNumberVO();
				userIdSearch.setEq(userTbl.getId().doubleValue());
				List<Sorter> sorters = new ArrayList<>();
				sorters.add(new Sorter(UserActivityTblEnum.createdDate.name(), DBConstants.DIRECTION_DECCENT));
				UserActivitySCO userActivitySCO = new UserActivitySCO();
				userActivitySCO.setUserId(userIdSearch);
				userActivitySCO.setSorters(sorters);
				userActivitySCO.setMaxRecordPerPage(1);

				Page<UserActivityTbl> userActivityList = userActivityRepository.findAll(userActivitySCO);
				long seconds = 0;
				if (CollectionUtils.isNotEmpty(userActivityList.getContent())) {
					UserActivityTbl userActivity = userActivityList.getContent().get(0);
					seconds = (new Date().getTime() - userActivity.getCreatedDate().getTime()) / 1000;

					userStatus.setStatus(userActivity.getActivity().getTypeName());
					userStatus.setDurationSeconds(seconds);
					userStatus.setDurationTime(MyDateUtils.formatDate(seconds));
				}

				listElement.add(userStatus);
			}

			// Set data return
			PageResultVO<UserStatusVO> data = new PageResultVO<UserStatusVO>();
			data.setElements(listElement);
			data.setCurrentPage(sco.getPageToGet());
			data.setPageSize(sco.getMaxRecordPerPage());

			// Fill total page and element
			Page<UserTbl> page = userRepository.findAll(sco);
			data.setTotalPage(page.getTotalPages());
			data.setTotalElement(page.getTotalElements());

			result.setData(data);
		}

		// Return
		return result;
	}

	/**
	 * Get User by id
	 * 
	 * @param UserId
	 * @return
	 */
	public APIResponse<Object> getById(Long id) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get Data
		Optional<UserTbl> tbl = userRepository.findById(id);

		// Return if User existed
		if (!tbl.isPresent()) {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// Transform data
		UserVO UserVO = userTransformer.convertToVO(tbl.get());

		// Return if not the owner
		if (result.getStatus() == null && !isDataOwner(UserVO.getUsername())) {
			result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_DATA, null));
		}

		// Set return data
		result.setData(UserVO);

		// Return
		return result;
	}

	/**
	 * Get User by id
	 * 
	 * @param UserId
	 * @return
	 */
	public UserTbl getTblById(Long id) {
		// Result
		UserTbl result = new UserTbl();

		// Get Data
		Optional<UserTbl> tbl = userRepository.findById(id);
		if (tbl.isPresent()) {
			result = tbl.get();
		}

		// Return
		return result;
	}

	/**
	 * Get User by id
	 * 
	 * @param UserId
	 * @return
	 */
	public UserVO getVoById(Long id) {
		// Result
		UserVO result = new UserVO();

		// Get Data
		Optional<UserTbl> tbl = userRepository.findById(id);
		if (tbl.isPresent()) {
			result = userTransformer.convertToVO(tbl.get());
		}

		// Return
		return result;
	}

	/**
	 * Search User by condition
	 * 
	 * Only creator
	 *
	 * @param UserId
	 * @return
	 */
	public APIResponse<Object> search(UserSCO sco) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get data
		Page<UserTbl> page = userRepository.findAll(sco);

		// Transformer
		PageResultVO<UserVO> data = userTransformer.convertToPageReturn(page);

		// Set data return
		result.setData(data);

		// Return
		return result;
	}

	/**
	 * 
	 * Get User by username
	 *
	 * @param userName
	 * @return
	 */
	public UserVO getByUserName(String userName) {
		// Declare result
		UserVO result = null;

		UserTbl tbl = userRepository.findFirstByUsername(userName);
		if (ObjectUtils.isNotEmpty(tbl)) {
			result = userTransformer.convertToVO(tbl);
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Get User by username
	 *
	 * @param userName
	 * @return
	 */
	public UserTbl getTblByUserName(String userName) {
		return userRepository.findFirstByUsername(userName);
	}

	/**
	 * Valid if data is belong to logged user
	 *
	 * @return
	 */
	public boolean isDataOwner(String username) {
		Boolean result = true;

		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Only check for role User, skip other role
		if (StringUtils.equals(loggedUser.getRole().getRoleName(), DBConstants.USER_ROLE_USER)) {
			if (!StringUtils.equals(loggedUser.getUsername(), username)) {
				result = false;
			}
		}

		// Return
		return result;
	}

	/**
	 * Logout
	 *
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> logout(UserVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();

			// username must not null
			if (StringUtils.isBlank(vo.getUsername())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.username.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Object>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Check if user is existed
		if (result.getStatus() == null) {
			UserTbl userTbl = getTblByUserName(vo.getUsername());
			if (userTbl == null) {
				result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.INEXISTED_USERNAME, vo.getUsername()));
			} else {
				// RECORD activity "LOGOUT"
				recordUserActivity(DBConstants.USER_ACTIVITY_LOGOUT);
			}
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Change private key
	 * 
	 * @param UserVO
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> changePrivateKey(ChangePrivateKeyVO vo) {
		// Declare result
		APIResponse<Object> result = new APIResponse<Object>();

		// Get current logged in user
		UserTbl tbl = userDetailsServiceImpl.getCurrentLoginUser();

		// Validate input
		if (result.getStatus() == null) {
			List<String> errors = new ArrayList<>();
			if (tbl.getPrivateKey() != null && StringUtils.isBlank(vo.getOldPrivateKey())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, "old private key"));
			}

			if (StringUtils.isBlank(vo.getNewPrivateKey())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.privateKey.name()));
			}

			// Record error
			if (CollectionUtils.isNotEmpty(errors)) {
				result = new APIResponse<Object>(HttpStatus.BAD_REQUEST, errors);
			}
		}

		// Validate old private key
		if (result.getStatus() == null && tbl.getPrivateKey() != null) {
			if (!mdpService.isValidPassword(vo.getOldPrivateKey(), tbl.getPrivateKey().getMdp())) {
				result = new APIResponse<Object>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_USER, null));
			}
		}

		// Update private key
		if (result.getStatus() == null) {
			tbl.setPrivateKey(mdpService.getMdp(vo.getNewPrivateKey()));
			userRepository.save(tbl);
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Record user activity
	 *
	 */
	protected void recordUserActivity(String activity) {
		UserTbl userLogin = userDetailsServiceImpl.getCurrentLoginUser();
		if (userLogin != null) {
			userActivityService.recordUserActivity(userLogin, activity);
		}
	}
}
