package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.BittrexPairTbl;
import com.hientran.sohebox.sco.BittrexPairSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.specification.BittrexPairSpecs;

/**
 * @author hientran
 */
public interface BittrexPairRepository
        extends JpaRepository<BittrexPairTbl, Long>, JpaSpecificationExecutor<BittrexPairTbl>, BaseRepository {

    BittrexPairSpecs specs = new BittrexPairSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<BittrexPairTbl> findAll(BittrexPairSCO sco) {

        // Declare result
        Page<BittrexPairTbl> result = null;

        // Create data filter
        Specification<BittrexPairTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<BittrexPairTbl> pageData = findAll(specific, pageable);

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
    public default BittrexPairTbl findByMarketName(String marketNameValue) {
        // Declare result
        BittrexPairTbl result = null;

        // Search result
        SearchTextVO marketName = new SearchTextVO();
        marketName.setEq(marketNameValue);
        BittrexPairSCO sco = new BittrexPairSCO();
        sco.setMarketName(marketName);

        Page<BittrexPairTbl> searchData = findAll(sco);

        // Process
        if (CollectionUtils.isNotEmpty(searchData.getContent())) {
            result = searchData.getContent().get(0);
        }

        // Return
        return result;
    }

    /**
     * Get all pair with extract = true
     *
     * @param market
     *            name value
     * @return
     */
    public default List<String> getAllPairToExtract() {
        // Declare result
        List<String> result = null;

        // Search result
        BittrexPairSCO sco = new BittrexPairSCO();
        sco.setExtractDataFlag(true);

        Page<BittrexPairTbl> searchData = findAll(sco);

        // Process
        if (CollectionUtils.isNotEmpty(searchData.getContent())) {
            result = new ArrayList<>();
            for (BittrexPairTbl tbl : searchData.getContent()) {
                if (tbl.getActive() == true) {
                    result.add(tbl.getMarketName());
                }
            }
        }

        // Return
        return result;
    }
}
