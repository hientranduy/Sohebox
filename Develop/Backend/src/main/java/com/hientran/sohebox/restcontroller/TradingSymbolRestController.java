package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.entity.TradingSymbolTbl;
import com.hientran.sohebox.service.TradingSymbolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TradingSymbolRestController extends BaseRestController {

	private final TradingSymbolService tradingSymbolService;

	/**
	 *
	 * Create
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(ApiPublicConstants.API_TRADING_SYMBOL)
	public ResponseEntity<?> create(@Validated @RequestBody TradingSymbolTbl request) {
		// Create TradingSymbol
		APIResponse<?> result = tradingSymbolService.create(request);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
