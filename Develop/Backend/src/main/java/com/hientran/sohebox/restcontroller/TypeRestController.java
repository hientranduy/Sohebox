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

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.TypeSCO;
import com.hientran.sohebox.vo.TypeVO;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TypeRestController extends BaseRestController {

	private final TypeCache typeCache;

	/**
	 * 
	 * Add new
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_TYPE)
	public ResponseEntity<?> create(@Validated @RequestBody TypeVO vo) {
		// Create Account
		APIResponse<?> result = typeCache.create(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	/**
	 * 
	 * Get type
	 */
	@GetMapping(ApiPublicConstants.API_TYPE + ApiPublicConstants.GET)
	public ResponseEntity<?> getType(@Parameter String typeClass, String typeCode) {

		TypeVO vo = typeCache.getType(typeClass, typeCode);
		APIResponse<TypeVO> result = new APIResponse<>();
		result.setData(vo);

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
	@PostMapping(ApiPublicConstants.API_TYPE + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody TypeSCO sco) {
		// Search
		APIResponse<?> result = typeCache.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}

	/**
	 * 
	 * Get all type class
	 *
	 * @return
	 */
	@GetMapping(ApiPublicConstants.API_TYPE + ApiPublicConstants.API_TYPE_CLASS)
	public ResponseEntity<?> getAllTypeClass() {
		// Get all User
		APIResponse<?> result = typeCache.getAllTypeClass();

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
	@PutMapping(ApiPublicConstants.API_TYPE)
	public ResponseEntity<?> update(@Validated @RequestBody TypeVO vo) {
		APIResponse<?> result = typeCache.updateType(vo);

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
	@GetMapping(ApiPublicConstants.API_TYPE + ApiPublicConstants.ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
		// Delete
		APIResponse<?> result = typeCache.getById(id);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}
}
