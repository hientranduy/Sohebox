package com.hientran.sohebox.restcontroller.admin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.cache.MediaTypeCache;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.restcontroller.BaseRestController;
import com.hientran.sohebox.sco.MediaTypeSCO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MediaTypeController extends BaseRestController {

	private final MediaTypeCache typeCache;

	/**
	 * Search
	 */
	@PostMapping(ApiPublicConstants.API_MEDIA_TYPE + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody MediaTypeSCO sco) {
		// Search
		APIResponse<?> result = typeCache.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * Update
	 */
	@PutMapping(ApiPublicConstants.API_MEDIA_TYPE)
	public ResponseEntity<?> update(@Validated @RequestBody MediaTypeTbl request) {
		APIResponse<?> result = typeCache.update(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
