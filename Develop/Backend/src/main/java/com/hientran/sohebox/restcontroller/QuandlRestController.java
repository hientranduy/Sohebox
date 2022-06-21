package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.service.QuandlService;

/**
 * @author hientran
 */
@RestController
public class QuandlRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private QuandlService quandlService;

    /**
     * 
     * Get WTI oil price
     *
     */
    @GetMapping(ApiPublicConstants.API_QUANDL + ApiPublicConstants.API_QUANDL_OPEC_ORB)
    public ResponseEntity<?> getWTIOilPrices() {
        APIResponse<?> result = quandlService.searchWTIOilPrices();

        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

    }
}
