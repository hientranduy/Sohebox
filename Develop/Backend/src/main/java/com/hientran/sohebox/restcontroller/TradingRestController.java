package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.service.TradingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TradingRestController extends BaseRestController {

	private final TradingService tradingService;

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
