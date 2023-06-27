package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping(ApiPublicConstants.API_TYPE + ApiPublicConstants.GET)
	public ResponseEntity<?> get(@Parameter String typeClass, String typeCode) {
		APIResponse<TypeVO> result = new APIResponse<>();
		result.setData(typeCache.getType(typeClass, typeCode));

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	@PostMapping(ApiPublicConstants.API_TYPE)
	public ResponseEntity<?> create(@Validated @RequestBody TypeVO vo) {
		// Create Account
		APIResponse<?> result = typeCache.create(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	@PutMapping(ApiPublicConstants.API_TYPE)
	public ResponseEntity<?> update(@Validated @RequestBody TypeVO vo) {
		APIResponse<?> result = typeCache.update(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

	@PostMapping(ApiPublicConstants.API_TYPE + ApiPublicConstants.SEARCH)
	public ResponseEntity<?> search(@RequestBody TypeSCO sco) {
		// Search
		APIResponse<?> result = typeCache.search(sco);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

	}
}
