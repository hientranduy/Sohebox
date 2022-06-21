package com.hientran.sohebox.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hientran.sohebox.constants.ApiPublicConstants;
import com.hientran.sohebox.entity.BittrexPairTbl;
import com.hientran.sohebox.service.BittrexService;

/**
 * @author hientran
 */
@RestController
public class BittrexRestController extends BaseRestController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private BittrexService bittrexService;

    /**
     * 
     * Get a all pairs that have extract flag = true
     *
     * @return
     */
    @GetMapping(ApiPublicConstants.API_BITTREX_PAIR_EXTRACT)
    public List<String> getExtractionPairs() {
        return bittrexService.getExtractionPairs();
    }

    /**
     * 
     * Update pairs _ extract flag
     *
     * @param pairs
     * @return
     */
    @PutMapping(ApiPublicConstants.API_BITTREX_PAIR_EXTRACT)
    public ResponseEntity<?> updateExtractionPairs(@Validated @RequestBody List<BittrexPairTbl> pairs) {
        bittrexService.updateExtractionPairs(pairs);
        return ResponseEntity.ok().build();
    }

}
