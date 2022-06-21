package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.EnglishUserGradeTbl;
import com.hientran.sohebox.sco.EnglishUserGradeSCO;
import com.hientran.sohebox.specification.EnglishUserGradeSpecs;

/**
 * @author hientran
 */
public interface EnglishUserGradeRepository extends JpaRepository<EnglishUserGradeTbl, Long>,
        JpaSpecificationExecutor<EnglishUserGradeTbl>, BaseRepository {

    EnglishUserGradeSpecs specs = new EnglishUserGradeSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<EnglishUserGradeTbl> findAll(EnglishUserGradeSCO sco) {

        // Declare result
        Page<EnglishUserGradeTbl> result = null;

        // Create data filter
        Specification<EnglishUserGradeTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<EnglishUserGradeTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }
}
