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
import com.hientran.sohebox.constants.enums.BittrexMarketSummary24hTblEnum;
import com.hientran.sohebox.entity.BittrexMarketSummary24hTbl;
import com.hientran.sohebox.sco.BittrexMarketSummary24hSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.BittrexMarketSummary24hSpecs;

/**
 * @author hientran
 */
public interface BittrexMarketSummary24hRepository extends JpaRepository<BittrexMarketSummary24hTbl, Long>,
        JpaSpecificationExecutor<BittrexMarketSummary24hTbl>, BaseRepository {

    BittrexMarketSummary24hSpecs specs = new BittrexMarketSummary24hSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<BittrexMarketSummary24hTbl> findAll(BittrexMarketSummary24hSCO sco) {

        // Declare result
        Page<BittrexMarketSummary24hTbl> result = null;

        // Create data filter
        Specification<BittrexMarketSummary24hTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<BittrexMarketSummary24hTbl> pageData = findAll(specific, pageable);

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
    public default BittrexMarketSummary24hTbl findLatestByMarketName(String marketNameValue) {
        // Declare result
        BittrexMarketSummary24hTbl result = null;

        // Search result
        SearchTextVO marketName = new SearchTextVO();
        marketName.setEq(marketNameValue);

        Sorter sort = new Sorter();
        sort.setProperty(BittrexMarketSummary24hTblEnum.timeStamp.name());
        sort.setDirection(DBConstants.DIRECTION_DECCENT);
        List<Sorter> sorters = new ArrayList<>();
        sorters.add(sort);

        BittrexMarketSummary24hSCO sco = new BittrexMarketSummary24hSCO();
        sco.setMarketName(marketName);
        sco.setMaxRecordPerPage(1);
        sco.setSorters(sorters);

        Page<BittrexMarketSummary24hTbl> searchData = findAll(sco);

        // Process
        if (CollectionUtils.isNotEmpty(searchData.getContent())) {
            result = searchData.getContent().get(0);
        }

        // Return
        return result;
    }
}
