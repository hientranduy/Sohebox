package com.hientran.sohebox.restcontroller.admin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.cache.FoodTypeCache;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.entity.FoodTypeTbl;
import com.hientran.sohebox.restcontroller.BaseRestController;
import com.hientran.sohebox.sco.FoodTypeSCO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FoodTypeController extends BaseRestController {
	private final FoodTypeCache typeCache;

	/**
	 * Search
	 */
	@PostMapping(ApiPublicConstants.API_FOOD_TYPE + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody FoodTypeSCO sco) {
		// Search
		APIResponse<?> result = typeCache.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * Update
	 */
	@PutMapping(ApiPublicConstants.API_FOOD_TYPE)
	public ResponseEntity<?> update(@Validated @RequestBody FoodTypeTbl request) {
		APIResponse<?> result = typeCache.update(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
