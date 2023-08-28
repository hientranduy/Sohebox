package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.ChangePrivateKeyVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.UserSCO;
import com.hientran.sohebox.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * Change password
	 */
	@PutMapping(ApiPublicConstants.API_USER + ApiPublicConstants.CHANGE_PASSWORD_LOGGED_USER)
	public ResponseEntity<?> changePasswordLoggedUser(@Validated @RequestBody UserTbl request) {
		// Change password
		APIResponse<?> result = userService.changePassword(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Change password by username
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_USER + ApiPublicConstants.CHANGE_PRIVATE_KEY)
	public ResponseEntity<?> changePrivateKey(@Validated @RequestBody ChangePrivateKeyVO vo) {
		// Change password
		APIResponse<?> result = userService.changePrivateKey(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * Create
	 */
	@PostMapping(ApiPublicConstants.API_USER)
	public ResponseEntity<?> createUser(@Validated @RequestBody UserTbl request) {
		// Create User
		APIResponse<?> result = userService.create(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * Logout
	 */
	@PostMapping(ApiPublicConstants.API_USER + ApiPublicConstants.LOGOUT)
	public ResponseEntity<?> logout() {
		// Create Account
		APIResponse<?> result = userService.logout();

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_USER + ApiPublicConstants.API_ACTIVE_USER)
	public ResponseEntity<?> searchActiveUser(@RequestBody UserSCO sco) {
		// Get all User
		APIResponse<?> result = userService.searchActiveUser(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_USER + ApiPublicConstants.API_USER_STATUS)
	public ResponseEntity<?> searchStatus(@RequestBody UserSCO sco) {
		// Get all User
		APIResponse<?> result = userService.searchUserStatus(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Update info
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_USER)
	public ResponseEntity<?> updateUser(@Validated @RequestBody UserTbl request) {
		// Update User
		APIResponse<?> result = userService.update(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}
}
