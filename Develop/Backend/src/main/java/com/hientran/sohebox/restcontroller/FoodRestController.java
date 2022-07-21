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

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.FoodSCO;
import com.hientran.sohebox.service.FoodService;
import com.hientran.sohebox.vo.FoodVO;

/**
 * @author hientran
 */
@RestController
public class FoodRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private FoodService foodService;

    /**
     * 
     * Create
     *
     * @param vo
     * @return
     */
    @PostMapping(ApiPublicConstants.API_FOOD)
    public ResponseEntity<?> create(@Validated
    @RequestBody
    FoodVO vo) {
        // Create Food
        APIResponse<?> result = foodService.create(vo);

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
    @PutMapping(ApiPublicConstants.API_FOOD)
    public ResponseEntity<?> update(@Validated
    @RequestBody
    FoodVO vo) {
        // Update Account
        APIResponse<?> result = foodService.update(vo);

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
    @PostMapping(ApiPublicConstants.API_FOOD + ApiPublicConstants.SEARCH)
    public ResponseEntity<?> search(@RequestBody
    FoodSCO sco) {
        // Search
        APIResponse<?> result = foodService.search(sco);

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
    @GetMapping(ApiPublicConstants.API_FOOD + ApiPublicConstants.ID)
    public ResponseEntity<?> getById(@PathVariable(value = "id")
    Long id) {
        // Delete
        APIResponse<?> result = foodService.getById(id);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
    }

}
