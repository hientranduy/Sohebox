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

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.sco.CryptoTokenConfigSCO;
import com.hientran.sohebox.service.CryptoTokenConfigService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CryptoTokenConfigRestController extends BaseRestController {

	private final CryptoTokenConfigService cryptoTokenConfigService;

	/**
	 * Add new
	 */
	@PostMapping(ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG)
	public ResponseEntity<?> create(@Validated @RequestBody CryptoTokenConfigTbl request) {
		// Create Account
		APIResponse<?> result = cryptoTokenConfigService.create(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * Get by ID
	 */
	@GetMapping(ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG + ApiPublicConstants.ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
		// Delete
		APIResponse<?> result = cryptoTokenConfigService.getById(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * Search
	 */
	@PostMapping(ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody CryptoTokenConfigSCO sco) {
		// Search
		APIResponse<?> result = cryptoTokenConfigService.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * Update
	 */
	@PutMapping(ApiPublicConstants.API_CRYPTO_TOKEN_CONFIG)
	public ResponseEntity<?> update(@Validated @RequestBody CryptoTokenConfigTbl request) {
		APIResponse<?> result = cryptoTokenConfigService.update(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
