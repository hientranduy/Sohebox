package com.hientran.sohebox.repository;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.BittrexCryptoTbl;
import com.hientran.sohebox.sco.BittrexCryptoSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.BittrexCryptoSpecs;

/**
 * @author hientran
 */
public interface BittrexCryptoRepository extends JpaRepository<BittrexCryptoTbl, Long>, JpaSpecificationExecutor<BittrexCryptoTbl>, BaseRepository {

    BittrexCryptoSpecs specs = new BittrexCryptoSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<BittrexCryptoTbl> findAll(BittrexCryptoSCO sco) {

        // Declare result
        Page<BittrexCryptoTbl> result = null;

        // Create data filter
        Specification<BittrexCryptoTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(), sco.getReportFlag());

        // Get data
        Page<BittrexCryptoTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }

    /**
     * Find by market name
     *
     * @param market
     *            name value
     * @return
     */
    public default BittrexCryptoTbl findByCurrency(String currencyValue) {
        // Declare result
        BittrexCryptoTbl result = null;

        // Search result
        SearchTextVO currency = new SearchTextVO();
        currency.setEq(currencyValue);
        BittrexCryptoSCO sco = new BittrexCryptoSCO();
        sco.setCurrency(currency);

        Page<BittrexCryptoTbl> searchData = findAll(sco);

        // Process
        if (CollectionUtils.isNotEmpty(searchData.getContent())) {
            result = searchData.getContent().get(0);
        }

        // Return
        return result;
    }
}
