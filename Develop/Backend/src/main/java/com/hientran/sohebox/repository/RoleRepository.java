package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.RoleTbl;
import com.hientran.sohebox.sco.RoleSCO;
import com.hientran.sohebox.specification.RoleSpecs;

/**
 * @author hientran
 */
public interface RoleRepository
        extends JpaRepository<RoleTbl, Long>, JpaSpecificationExecutor<RoleTbl>, BaseRepository {

    RoleSpecs specs = new RoleSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<RoleTbl> findAll(RoleSCO sco) {

        // Declare result
        Page<RoleTbl> result = null;

        // Create data filter
        Specification<RoleTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<RoleTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }
}
