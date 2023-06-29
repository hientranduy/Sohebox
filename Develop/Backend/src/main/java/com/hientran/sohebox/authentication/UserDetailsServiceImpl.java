package com.hientran.sohebox.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsImp userDetailsImp = null;

		// Get user by user name
		UserTbl userTbl = userRepository.findFirstByUsername(username);
		if (userTbl == null) {
			throw new UsernameNotFoundException("User does not exist.");
		} else {
			if (userDetailsImp == null) {
				userDetailsImp = new UserDetailsImp();
			}
			userDetailsImp.setUserTbl(userTbl);
		}

		return userDetailsImp;
	}

	/**
	 * Get info of current logged in user
	 *
	 * @return
	 */
	public UserTbl getCurrentLoginUser() {
		// Declare result
		UserTbl result = null;

		// Get Data
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetailsImp) {
			result = ((UserDetailsImp) principal).getUserTbl();
		}

		// Return
		return result;
	}
}
