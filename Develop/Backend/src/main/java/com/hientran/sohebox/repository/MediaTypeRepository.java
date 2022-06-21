package com.hientran.sohebox.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.enums.MediaTypeTblEnum;
import com.hientran.sohebox.entity.MediaTypeTbl;
import com.hientran.sohebox.sco.MediaTypeSCO;
import com.hientran.sohebox.sco.Sorter;
import com.hientran.sohebox.specification.MediaTypeSpecs;

/**
 * @author hientran
 */
public interface MediaTypeRepository
        extends JpaRepository<MediaTypeTbl, Long>, JpaSpecificationExecutor<MediaTypeTbl>, BaseRepository {
    MediaTypeSpecs specs = new MediaTypeSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<MediaTypeTbl> findAll(MediaTypeSCO sco) {

        // Declare result
        Page<MediaTypeTbl> result = null;

        // Create data filter
        Specification<MediaTypeTbl> specific = specs.buildSpecification(sco);

        // Set default sort if not have
        if (sco.getSorters() == null) {
            // Sort default by type code
            Sorter sort = new Sorter();
            sort.setDirection(DBConstants.DIRECTION_ACCENT);
            sort.setProperty(MediaTypeTblEnum.typeCode.name());

            List<Sorter> sorters = new ArrayList<>();
            sorters.add(sort);

            sco.setSorters(sorters);
        }

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<MediaTypeTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }

    /**
     * Get all type class
     */
    @SuppressWarnings("unchecked")
    public default List<Object[]> getAllTypeClass() {
        // Declare result
        List<Object[]> result = null;

        // Prepare native SQL
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT type_class      ");
        sql.append("      , 1               ");
        sql.append(" FROM   type_tbl        ");
        sql.append(" GROUP  BY type_class   ");
        sql.append(" ORDER  BY type_class   ");

        // Execute SQL
        Query query = getEntityManager().createNativeQuery(sql.toString());
        result = query.getResultList();

        // Return
        return result;
    }
}
