package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.UserSCO;
import com.hientran.sohebox.security.UserService;
import com.hientran.sohebox.vo.ChangePasswordVO;
import com.hientran.sohebox.vo.ChangePrivateKeyVO;
import com.hientran.sohebox.vo.UserVO;

/**
 * @author hientran
 */
@RestController
public class UserRestController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * Add new
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_USER)
	public ResponseEntity<?> createUser(@Validated @RequestBody UserVO vo) {
		// Create User
		APIResponse<?> result = userService.create(vo);

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
	@PutMapping(ApiPublicConstants.API_USER + ApiPublicConstants.CHANGE_PASSWORD)
	public ResponseEntity<?> changePassword(@Validated @RequestBody ChangePasswordVO vo) {
		// Change password
		APIResponse<?> result = userService.changePassword(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Change password by logged user
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_USER + ApiPublicConstants.CHANGE_PASSWORD_LOGGED_USER)
	public ResponseEntity<?> changePasswordLoggedUser(@Validated @RequestBody ChangePasswordVO vo) {
		// Change password
		APIResponse<?> result = userService.changePassword(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Delete logged user
	 *
	 * @return
	 */
	@DeleteMapping(ApiPublicConstants.API_USER)
	public ResponseEntity<?> deleteLoggedUser() {
		// Change password
		APIResponse<?> result = userService.deleteLoggedUser();

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
	public ResponseEntity<?> updateUser(@Validated @RequestBody UserVO vo) {
		// Update User
		APIResponse<?> result = userService.update(vo);

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
	 * Get by ID
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(ApiPublicConstants.API_USER + ApiPublicConstants.ID)
	public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long id) {
		// Get User by ID
		APIResponse<?> result = userService.getById(id);

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
	@PostMapping(ApiPublicConstants.API_USER + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody UserSCO sco) {
		// Search User
		APIResponse<?> result = userService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * 
	 * Logout
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_USER + ApiPublicConstants.LOGOUT)
	public ResponseEntity<?> logout(@Validated @RequestBody UserVO vo) {
		// Create Account
		APIResponse<?> result = userService.logout(vo);

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
}
