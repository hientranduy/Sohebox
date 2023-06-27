package com.hientran.sohebox.restcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.service.TradingSymbolService;
import com.hientran.sohebox.vo.TradingSymbolVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
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
	public ResponseEntity<?> create(@Validated @RequestBody TradingSymbolVO vo) {
		// Create TradingSymbol
		APIResponse<?> result = tradingSymbolService.create(vo);

		// Return
		return new ResponseEntity<>(result, new HttpHeaders(),
				result.getStatus() != null ? result.getStatus() : HttpStatus.OK);
	}

}
