package com.hientran.sohebox.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.MdpTbl;
import com.hientran.sohebox.repository.MdpRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MdpService {

	private final MdpRepository mdpRepository;

	private String encryptMdp(String mdp) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(mdp);
	}

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
			result = mdpRepository.save(result);
		}

		// Return
		return result;
	}

	public boolean isValidPassword(String newPassword, String oldPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(newPassword, oldPassword);
	}
}
