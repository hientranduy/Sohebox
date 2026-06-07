package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.authentication.UserDetailsServiceImpl;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.dto.ChangePrivateKeyVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.UserRepository;
import com.hientran.sohebox.specification.UserSpecs.UserTblEnum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService extends BaseService {

	@PersistenceContext
	protected EntityManager entityManager;

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final MdpService mdpService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * Change password logged user
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> changePassword(UserTbl request) {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();

		// Validate password
		List<String> errors = new ArrayList<>();
		if (isInvalidPassword(request.getPassword())) {
			errors.add(ResponseCode.mapParam(ResponseCode.INVALID_FIELD, UserTblEnum.password.name()));
		}
		if (CollectionUtils.isNotEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Change password
		UserTbl userTbl = userDetailsServiceImpl.getCurrentLoginUser();
		userTbl.setMdp(mdpService.getMdp(request.getPassword()));
		userRepository.save(userTbl);

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
		APIResponse<Object> result = new APIResponse<>();

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(vo.getNewPrivateKey())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, "new private key"));
		}
		if (CollectionUtils.isNotEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Get current logged in user
		UserTbl loggedUser = userDetailsServiceImpl.getCurrentLoginUser();

		// Validate old private key if found
		if (ObjectUtils.isNotEmpty(loggedUser.getPrivateKey())) {
			// Required
			if (StringUtils.isBlank(vo.getOldPrivateKey())) {
				errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, "old private key"));
			}
			if (CollectionUtils.isNotEmpty(errors)) {
				return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
			}

			// Check equal
			if (!mdpService.isValidPassword(vo.getOldPrivateKey(), loggedUser.getPrivateKey().getMdp())) {
				return new APIResponse<>(HttpStatus.BAD_REQUEST,
						ResponseCode.mapParam(ResponseCode.UNAUTHORIZED_USER, null));
			}
		}

		// Update private key
		loggedUser.setPrivateKey(mdpService.getMdp(vo.getNewPrivateKey()));
		userRepository.save(loggedUser);

		// Return
		return result;
	}

	/**
	 * Create user
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Long> create(UserTbl request) {

		// Validate input
		List<String> errors = new ArrayList<>();
		if (StringUtils.isBlank(request.getUsername())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.username.name()));
		}
		if (isInvalidPassword(request.getPassword())) {
			errors.add(ResponseCode.mapParam(ResponseCode.INVALID_FIELD, UserTblEnum.password.name()));
		}
		if (StringUtils.isBlank(request.getFirstName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.firstName.name()));
		}
		if (StringUtils.isBlank(request.getLastName())) {
			errors.add(ResponseCode.mapParam(ResponseCode.FILED_EMPTY, UserTblEnum.lastName.name()));
		}
		if (CollectionUtils.isNotEmpty(errors)) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST, errors);
		}

		// Valid existed record
		if (getByUserName(request.getUsername()) != null) {
			return new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.EXISTED_USERNAME, request.getUsername()));
		}

		// Create User
		UserTbl tbl = request;

		// Always set role "user"
		tbl.setRole(roleService.getByRoleName(DBConstants.USER_ROLE_USER));

		// Set mdp
		tbl.setMdp(mdpService.getMdp(request.getPassword()));

		// Set avatar
		if (StringUtils.isBlank(request.getAvatarUrl())) {
			tbl.setAvatarUrl(DBConstants.USER_DEFAULT_AVATAR);
		}

		// Private key must be null when create new account
		tbl.setPrivateKey(null);

		// Create User
		tbl = userRepository.save(tbl);

		// Return
		APIResponse<Long> result = new APIResponse<>();
		result.setData(tbl.getId());
		return result;
	}

	/**
	 *
	 * Get User by username
	 *
	 * @param userName
	 * @return
	 */
	public UserTbl getByUserName(String userName) {
		return userRepository.findFirstByUsername(userName);
	}

	/**
	 * Get User by id
	 */
	public UserTbl getTblById(Long id) {
		Optional<UserTbl> tbl = userRepository.findById(id);
		if (tbl.isPresent()) {
			return tbl.get();
		}
		return null;
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
		boolean result = true;

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

	public Boolean isInvalidPassword(String input) {
		boolean result = false;
		if (StringUtils.isBlank(input)) {
			result = true;
		} else {
			if (input.length() < 6) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Logout
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> logout() {
		return new APIResponse<>();
	}

	/**
	 * Update logged user
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public APIResponse<Object> update(UserTbl request) {
		// Update
		UserTbl updateTbl = userDetailsServiceImpl.getCurrentLoginUser();
		if (StringUtils.isNotEmpty(request.getFirstName())) {
			updateTbl.setFirstName(request.getFirstName());
		}
		if (StringUtils.isNotEmpty(request.getLastName())) {
			updateTbl.setLastName(request.getLastName());
		}
		userRepository.save(updateTbl);

		// Return
		APIResponse<Object> result = new APIResponse<>();
		result.setData(updateTbl);
		return result;
	}
}
