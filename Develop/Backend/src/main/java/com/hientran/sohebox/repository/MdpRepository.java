package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.MdpTbl;
import com.hientran.sohebox.sco.MdpSCO;
import com.hientran.sohebox.specification.MdpSpecs;

/**
 * @author hientran
 */
public interface MdpRepository extends JpaRepository<MdpTbl, Long>, JpaSpecificationExecutor<MdpTbl>, BaseRepository {

    MdpSpecs specs = new MdpSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<MdpTbl> findAll(MdpSCO sco) {

        // Declare result
        Page<MdpTbl> result = null;

        // Create data filter
        Specification<MdpTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<MdpTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }
}
