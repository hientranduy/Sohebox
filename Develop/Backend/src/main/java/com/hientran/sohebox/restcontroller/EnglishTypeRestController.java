package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.hientran.sohebox.cache.EnglishTypeCache;
import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.EnglishTypeSCO;
import com.hientran.sohebox.vo.EnglishTypeVO;

/**
 * @author hientran
 */
@RestController
public class EnglishTypeRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EnglishTypeCache typeCache;

    /**
     * 
     * Search
     *
     * @param sco
     * @return
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
     * 
     * Get all type class
     *
     * @return
     */
    @GetMapping(ApiPublicConstants.API_ENGLISH_TYPE + ApiPublicConstants.API_TYPE_CLASS)
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
    @PutMapping(ApiPublicConstants.API_ENGLISH_TYPE)
    public ResponseEntity<?> update(@Validated @RequestBody EnglishTypeVO vo) {
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
    @GetMapping(ApiPublicConstants.API_ENGLISH_TYPE + ApiPublicConstants.ID)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        // Delete
        APIResponse<?> result = typeCache.getById(id);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
    }

}
