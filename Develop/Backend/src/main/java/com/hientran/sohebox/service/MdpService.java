package com.hientran.sohebox.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.MdpTbl;
import com.hientran.sohebox.repository.MdpRepository;
import com.hientran.sohebox.sco.MdpSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.utils.LogUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MdpService {

	private final MdpRepository mdpRepository;

	/**
	 * Prepare mdp
	 *
	 * @param roleName
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public MdpTbl getMdp(String mdp) {
		Validate.notNull(mdp, "Fail validation, Password is null");

		// Declare result
		MdpTbl result = null;

		// Get all mdp
		List<MdpTbl> listMdp = mdpRepository.findAll();
		if (CollectionUtils.isNotEmpty(listMdp)) {
			for (MdpTbl item : listMdp) {
				if (isValidPassword(mdp, item.getMdp())) {
					result = item;
					break;
				}
			}
		}

		// Create new if empty
		if (result == null) {
			result = new MdpTbl();
			result.setMdp(encryptMdp(mdp));
			result.setDescription(mdp);
			Long mdpId = create(result);

			result = getById(mdpId);
		}

		// Return
		return result;
	}

	/**
	 * Get mdp by id
	 *
	 * @param id
	 * @return
	 */
	public MdpTbl getById(Long id) {
		// Declare result
		MdpTbl result = null;

		try {
			// Get Data
			Optional<MdpTbl> tbl = mdpRepository.findById(id);

			// Transformer
			if (tbl.isPresent()) {
				result = tbl.get();
			}

		} catch (Exception e) {
			LogUtils.writeLogError(e);
			throw e;
		}

		// Return
		return result;
	}

	/**
	 * Get mdp by id
	 *
	 * @param mdpId
	 * @return
	 */
	public MdpTbl getByMdp(String mdp) {
		// Declare result
		MdpTbl result = null;

		try {
			// Get Data
			SearchTextVO mdpSearch = new SearchTextVO();
			mdpSearch.setEq(mdp);
			MdpSCO sco = new MdpSCO();
			sco.setMdp(mdpSearch);
			Page<MdpTbl> page = mdpRepository.findAll(sco);

			// Transformer
			if (CollectionUtils.isNotEmpty(page.getContent())) {
				result = page.getContent().get(0);
			}

		} catch (Exception e) {
			LogUtils.writeLogError(e);
			throw e;
		}

		// Return
		return result;
	}

	/**
	 * Add a mdp
	 *
	 * @param mdp
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Long create(MdpTbl tbl) {
		// Declare result
		Long result = null;

		try {
			// Add mdp
			tbl = mdpRepository.save(tbl);
			result = tbl.getId();

		} catch (Exception e) {
			LogUtils.writeLogError(e);
			throw e;
		}

		// Return
		return result;
	}

	/**
	 * Update a mdp
	 *
	 * @param mdp
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean update(MdpTbl tbl) {
		// Declare result
		boolean result = false;

		try {
			// Get old record
			Optional<MdpTbl> mdpTbl = mdpRepository.findById(tbl.getId());

			// Update if found, else return not found exception
			if (mdpTbl.isPresent()) {
				MdpTbl updateTbl = mdpTbl.get();
				updateTbl.setMdp(tbl.getMdp());
				updateTbl.setDescription(tbl.getDescription());
				mdpRepository.save(updateTbl);
				result = true;
			}
		} catch (Exception e) {
			LogUtils.writeLogError(e);
			throw e;
		}

		// Return
		return result;
	}

	/**
	 * Delete a mdp
	 *
	 * @param mdp
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(Long id) {
		// Declare result
		boolean result = false;

		try {
			// Check existed
			Optional<MdpTbl> mdpTbl = mdpRepository.findById(id);

			// Delete if found, else return not found exception
			if (mdpTbl.isPresent()) {
				mdpRepository.delete(mdpTbl.get());
				result = true;
			}
		} catch (Exception e) {
			LogUtils.writeLogError(e);
			throw e;
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Encode password
	 *
	 * @param mdp
	 * @return
	 */
	private String encryptMdp(String mdp) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(mdp);
	}

	/**
	 * 
	 * Check password
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @return true/false
	 */
	public boolean isValidPassword(String newPassword, String oldPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(newPassword, oldPassword);
	}
}
