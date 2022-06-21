package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.enums.BittrexMarketHistoryTblEnum;
import com.hientran.sohebox.entity.BittrexMarketHistoryTbl;
import com.hientran.sohebox.sco.BittrexMarketHistorySCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.BittrexMarketHistorySpecs;

/**
 * @author hientran
 */
public interface BittrexMarketHistoryRepository
        extends JpaRepository<BittrexMarketHistoryTbl, Long>, JpaSpecificationExecutor<BittrexMarketHistoryTbl>, BaseRepository {

    BittrexMarketHistorySpecs specs = new BittrexMarketHistorySpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<BittrexMarketHistoryTbl> findAll(BittrexMarketHistorySCO sco) {

        // Declare result
        Page<BittrexMarketHistoryTbl> result = null;

        // Create data filter
        Specification<BittrexMarketHistoryTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(), sco.getReportFlag());

        // Get data
        Page<BittrexMarketHistoryTbl> pageData = findAll(specific, pageable);

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
    public default BittrexMarketHistoryTbl findLatestByMarketName(String pair) {
        // Declare result
        BittrexMarketHistoryTbl result = null;

        // Search result
        SearchTextVO marketName = new SearchTextVO();
        marketName.setEq(pair);

        Sorter sort = new Sorter();
        sort.setProperty(BittrexMarketHistoryTblEnum.timeStamp.name());
        sort.setDirection(DBConstants.DIRECTION_DECCENT);
        List<Sorter> sorters = new ArrayList<>();
        sorters.add(sort);

        BittrexMarketHistorySCO sco = new BittrexMarketHistorySCO();
        sco.setMarketName(marketName);
        sco.setMaxRecordPerPage(1);
        sco.setSorters(sorters);

        Page<BittrexMarketHistoryTbl> searchData = findAll(sco);

        // Process
        if (CollectionUtils.isNotEmpty(searchData.getContent())) {
            result = searchData.getContent().get(0);
        }

        // Return
        return result;
    }
}
