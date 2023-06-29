package com.hientran.sohebox.restcontroller.admin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.cache.EnglishTypeCache;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.restcontroller.BaseRestController;
import com.hientran.sohebox.sco.EnglishTypeSCO;
import com.hientran.sohebox.vo.EnglishTypeVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EnglishTypeController extends BaseRestController {

	private final EnglishTypeCache typeCache;

	/**
	 * Search
	 */
	@PostMapping(ApiPublicConstants.API_ENGLISH_TYPE + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody EnglishTypeSCO sco) {
		// Search
		APIResponse<?> result = typeCache.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * Update
	 */
	@PutMapping(ApiPublicConstants.API_ENGLISH_TYPE)
	public ResponseEntity<?> update(@Validated @RequestBody EnglishTypeVO vo) {
		APIResponse<?> result = typeCache.update(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}
}
