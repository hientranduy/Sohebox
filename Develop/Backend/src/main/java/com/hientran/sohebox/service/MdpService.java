package com.hientran.sohebox.service;

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
		MdpTbl result = mdpRepository.findFirstByDescription(mdp);
		if (result == null) {
			result = new MdpTbl();
			result.setMdp(encryptMdp(mdp));
			result.setDescription(mdp);
			result = mdpRepository.save(result);
		}
		return result;
	}

	public boolean isValidPassword(String newPassword, String oldPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(newPassword, oldPassword);
	}
}
