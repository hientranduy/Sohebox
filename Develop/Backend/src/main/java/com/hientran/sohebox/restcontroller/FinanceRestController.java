package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.service.DataExternalService;

/**
 * @author hientran
 */
@RestController
public class FinanceRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DataExternalService dataExternalService;

    /**
     * 
     * Get currency vietcombank
     *
     */
    @GetMapping(ApiPublicConstants.API_FINANCE + ApiPublicConstants.API_CURRENCY_VCB_RATE)
    public ResponseEntity<?> getCurrencyVCB() {
        APIResponse<?> result = dataExternalService.getVietcombankForeignerRate();

        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

    }

    /**
     * 
     * Get currency vietcombank
     *
     */
    @GetMapping(ApiPublicConstants.API_FINANCE + ApiPublicConstants.API_GOLD_SJC)
    public ResponseEntity<?> getGoldSJC() {
        APIResponse<?> result = dataExternalService.getSjcGoldPrice();

        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

    }
}
