package com.hientran.sohebox.restcontroller;

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
import com.hientran.sohebox.dto.AccountVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.sco.AccountSCO;
import com.hientran.sohebox.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountRestController extends BaseRestController {

	private final AccountService accountService;

	/**
	 *
	 * Add new
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ACCOUNT)
	public ResponseEntity<?> create(@Validated @RequestBody AccountVO vo) {
		// Create Account
		APIResponse<?> result = accountService.create(vo);

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
	@PostMapping(ApiPublicConstants.API_ACCOUNT + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody AccountSCO sco) {
		// Search
		APIResponse<?> result = accountService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 *
	 * Delete by ID
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(ApiPublicConstants.API_ACCOUNT + ApiPublicConstants.ID)
	public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id) {
		// Delete
		APIResponse<?> result = accountService.deleteById(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Update
	 *
	 * @param vo
	 * @return
	 */
	@PutMapping(ApiPublicConstants.API_ACCOUNT)
	public ResponseEntity<?> update(@Validated @RequestBody AccountVO vo) {
		// Update Account
		APIResponse<?> result = accountService.update(vo);

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
	@GetMapping(ApiPublicConstants.API_ACCOUNT + ApiPublicConstants.ID)
	public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long id) {
		APIResponse<?> result = accountService.getById(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 *
	 * Get clear password
	 *
	 * @param id
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_ACCOUNT + ApiPublicConstants.API_ACCOUNT_SHOW_PASSWORD)
	public ResponseEntity<?> showPassword(@RequestBody AccountVO vo) {
		APIResponse<?> result = accountService.showPassword(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
