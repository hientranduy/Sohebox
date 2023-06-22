package com.hientran.sohebox.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.EnglishLearnReportTbl;
import com.hientran.sohebox.sco.EnglishLearnReportSCO;
import com.hientran.sohebox.specification.EnglishLearnReportSpecs;

/**
 * @author hientran
 */
public interface EnglishLearnReportRepository extends JpaRepository<EnglishLearnReportTbl, Long>,
        JpaSpecificationExecutor<EnglishLearnReportTbl>, BaseRepository {

    EnglishLearnReportSpecs specs = new EnglishLearnReportSpecs();

    /**
     * 
     * Get all data
     *
     * @return
     */
    public default Page<EnglishLearnReportTbl> findAll(EnglishLearnReportSCO sco) {

        // Declare result
        Page<EnglishLearnReportTbl> result = null;

        // Create data filter
        Specification<EnglishLearnReportTbl> specific = specs.buildSpecification(sco);

        // Create page able
        Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
                sco.getReportFlag());

        // Get data
        Page<EnglishLearnReportTbl> pageData = findAll(specific, pageable);

        // Return
        result = pageData;
        return result;
    }

    /**
     * 
     * Find daily learn by user/count total
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public default List<Object[]> findDailyLearn(EntityManager entityManager) {
        // Declare result
        List<Object[]> result = null;

        // Prepare native SQL
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT user_id                ");
        sql.append("      , SUM(learned_today)     ");
        sql.append(" FROM english_learn_record_tbl ");
        sql.append(" WHERE DATE_FORMAT(updated_date, '%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 1 DAY)");
        sql.append(" GROUP BY user_id ");

        // Execute SQL
        Query query = entityManager.createNativeQuery(sql.toString());
        result = query.getResultList();

        // Return
        return result;
    }
}
