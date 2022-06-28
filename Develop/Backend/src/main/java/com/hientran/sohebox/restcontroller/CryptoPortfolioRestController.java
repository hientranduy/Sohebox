package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.sco.CryptoPortfolioSCO;
import com.hientran.sohebox.service.CryptoPortfolioService;
import com.hientran.sohebox.vo.CryptoPortfolioVO;

/**
 * @author hientran
 */
@RestController
public class CryptoPortfolioRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CryptoPortfolioService cryptoPortfolioService;

    /**
     * Add new
     */
    @PostMapping(ApiPublicConstants.API_CRYPTO_PORTFOLIO)
    public ResponseEntity<?> create(@Validated
    @RequestBody
    CryptoPortfolioVO vo) {
        // Create Account
        APIResponse<?> result = cryptoPortfolioService.create(vo);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
    }

    /**
     * Update
     */
    @PutMapping(ApiPublicConstants.API_CRYPTO_PORTFOLIO)
    public ResponseEntity<?> update(@Validated
    @RequestBody
    CryptoPortfolioVO vo) {
        // Update Account
        APIResponse<?> result = cryptoPortfolioService.update(vo);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
    }

    /**
     * Get by ID
     */
    @GetMapping(ApiPublicConstants.API_CRYPTO_PORTFOLIO + ApiPublicConstants.ID)
    public ResponseEntity<?> getById(@PathVariable(value = "id")
    Long id) {
        APIResponse<?> result = cryptoPortfolioService.getById(id);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
    }

    /**
     * Search
     */
    @PostMapping(ApiPublicConstants.API_CRYPTO_PORTFOLIO + ApiPublicConstants.SEARCH)
    public ResponseEntity<?> search(@RequestBody
    CryptoPortfolioSCO sco) {
        // Search
        APIResponse<?> result = cryptoPortfolioService.search(sco);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

    }

    /**
     * Delete by ID
     */
    @DeleteMapping(ApiPublicConstants.API_CRYPTO_PORTFOLIO + ApiPublicConstants.ID)
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id")
    Long id) {
        // Delete
        APIResponse<?> result = cryptoPortfolioService.deleteById(id);

        // Return
        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
    }

}
