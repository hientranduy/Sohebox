package com.hientran.sohebox.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.CountryTbl;
import com.hientran.sohebox.repository.CountryRepository;
import com.hientran.sohebox.sco.CountrySCO;
import com.hientran.sohebox.sco.SearchTextVO;

import lombok.RequiredArgsConstructor;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CountryService extends BaseService {
	private final CountryRepository countryRepository;

	/**
	 * 
	 * Get country
	 *
	 * @param key
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public CountryTbl get(String countryName) {
		// Declare result
		CountryTbl result = null;

		SearchTextVO nameSearch = new SearchTextVO();
		nameSearch.setEq(countryName);

		CountrySCO sco = new CountrySCO();
		sco.setName(nameSearch);

		// Get data
		List<CountryTbl> list = countryRepository.findAll(sco).getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0);
		} else {
			// Create new country
			CountryTbl tbl = new CountryTbl();
			tbl.setName(countryName);
			result = countryRepository.save(tbl);
		}

		// Return
		return result;
	}
}
