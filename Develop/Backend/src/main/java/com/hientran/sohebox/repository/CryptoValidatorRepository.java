package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.CryptoValidatorTbl;
import com.hientran.sohebox.sco.CryptoValidatorSCO;
import com.hientran.sohebox.specification.CryptoValidatorSpecs;

/**
 * @author hientran
 */
public interface CryptoValidatorRepository
        extends JpaRepository<CryptoValidatorTbl, Long>, JpaSpecificationExecutor<CryptoValidatorTbl>, BaseRepository {
    CryptoValidatorSpecs specs = new CryptoValidatorSpecs();

    /**
     * Get all data
     */
    public default Page<CryptoValidatorTbl> findAll(CryptoValidatorSCO sco) {

        // Declare result
        Page<CryptoValidatorTbl> result = null;

        // Create data filter
        Specification<CryptoValidatorTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<CryptoValidatorTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }

}
