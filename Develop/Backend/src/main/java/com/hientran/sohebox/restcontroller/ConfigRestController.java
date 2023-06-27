package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.cache.ConfigCache;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.ConfigSCO;
import com.hientran.sohebox.vo.ConfigVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@RestController
@RequiredArgsConstructor
public class ConfigRestController extends BaseRestController {

	private final ConfigCache configCache;

	/**
	 * 
	 * Search
	 *
	 * @param sco
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_CONFIG + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody ConfigSCO sco) {
		// Search
		APIResponse<?> result = configCache.search(sco);

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
	@PutMapping(ApiPublicConstants.API_CONFIG)
	public ResponseEntity<?> update(@Validated @RequestBody ConfigVO vo) {
		APIResponse<?> result = configCache.update(vo);

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
	@GetMapping(ApiPublicConstants.API_CONFIG + ApiPublicConstants.ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
		// Delete
		APIResponse<?> result = configCache.getById(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Add new
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_CONFIG)
	public ResponseEntity<?> create(@Validated @RequestBody ConfigVO vo) {
		// Create Account
		APIResponse<?> result = configCache.create(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
