package com.hientran.sohebox.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.service.TradingService;

/**
 * @author hientran
 */
@RestController
public class TradingRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private TradingService tradingService;

    /**
     * 
     * Get WTI oil price
     *
     */
    @GetMapping(ApiPublicConstants.API_TRADINGECONOMICS + ApiPublicConstants.API_TRADINGECONOMICS_PRICE_OIL)
    public ResponseEntity<?> getOilPrice() {
        APIResponse<?> result = tradingService.searchOilPrice();

        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

    }

    /**
     * 
     * Get WTI oil price
     *
     */
    @GetMapping(ApiPublicConstants.API_TRADINGECONOMICS + ApiPublicConstants.API_TRADINGECONOMICS_PRICE_STOCK)
    public ResponseEntity<?> getStockPrice() {
        APIResponse<?> result = tradingService.searchStockPrice();

        return new ResponseEntity<>(result, new HttpHeaders(),
                result.getStatus() != null ? result.getStatus() : HttpStatus.OK);

    }
}
