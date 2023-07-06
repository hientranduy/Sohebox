package com.hientran.sohebox.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.UserSCO;
import com.hientran.sohebox.specification.UserSpecs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public interface UserRepository
		extends JpaRepository<UserTbl, Long>, JpaSpecificationExecutor<UserTbl>, BaseRepository {

	UserTbl findFirstByUsername(String username);

	UserSpecs specs = new UserSpecs();

	/**
	 * 
	 * Get all data
	 *
	 * @return
	 */
	public default Page<UserTbl> findAll(UserSCO sco) {

		// Declare result
		Page<UserTbl> result = null;

		// Create data filter
		Specification<UserTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<UserTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

	/**
	 * 
	 * Get active users
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default List<Object[]> getActiveUser(UserSCO sco, EntityManager entityManager) {
		// Declare result
		List<Object[]> result = null;

		// Prepare native SQL
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT user_id, MAX(created_date)               ");
		sql.append(" FROM   user_activity_tbl                        ");
		sql.append(" WHERE  created_date >= NOW() - INTERVAL 3 MONTH ");
		sql.append(" GROUP  BY user_id                               ");
		sql.append(" ORDER BY MAX(created_date) DESC                 ");
		sql.append(" LIMIT ").append(sco.getMaxRecordPerPage());

		// Execute SQL
		Query query = entityManager.createNativeQuery(sql.toString());
		result = query.getResultList();

		// Return
		return result;
	}
}
