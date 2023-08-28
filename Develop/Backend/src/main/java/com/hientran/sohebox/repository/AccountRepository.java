package com.hientran.sohebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.AccountTbl;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.sco.AccountSCO;
import com.hientran.sohebox.specification.AccountSpecs;

public interface AccountRepository
		extends JpaRepository<AccountTbl, Long>, JpaSpecificationExecutor<AccountTbl>, BaseRepository {

	AccountSpecs specs = new AccountSpecs();

	/**
	 *
	 * Get all data
	 *
	 * @return
	 */
	public default Page<AccountTbl> findAll(AccountSCO sco) {

		// Declare result
		Page<AccountTbl> result = null;

		// Create data filter
		Specification<AccountTbl> specific = specs.buildSpecification(sco);

		// Create page able
		Pageable pageable = createPageable(sco.getPageToGet(), sco.getMaxRecordPerPage(), sco.getSorters(),
				sco.getReportFlag());

		// Get data
		Page<AccountTbl> pageData = findAll(specific, pageable);

		// Return
		result = pageData;
		return result;
	}

	AccountTbl findFirstByUserAndTypeAndAccountName(UserTbl user, TypeTbl type, String accountName);

}
