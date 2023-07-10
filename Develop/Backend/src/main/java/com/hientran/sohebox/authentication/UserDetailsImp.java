package com.hientran.sohebox.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hientran.sohebox.entity.UserTbl;

/**
 * Custom user details.
 *
 */
public class UserDetailsImp implements UserDetails {

	private static final long serialVersionUID = 5749884178519030353L;

	private UserTbl userTbl;

	/**
	 * Constructor
	 *
	 */
	public UserDetailsImp() {
		super();
	}

	/**
	 * Get userTbl
	 *
	 * @return UserTbl
	 */
	public UserTbl getUserTbl() {
		return userTbl;
	}

	/**
	 * Set userTbl
	 *
	 * @param UserTbl the UserTbl to set
	 */
	public void setUserTbl(UserTbl userTbl) {
		this.userTbl = userTbl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPassword() {
		return userTbl.getMdp().getMdp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername() {
		return userTbl.getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority("ROLE_" + userTbl.getRole().getRoleName()));
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		if (userTbl.isDeleteFlag()) {
			return false;
		} else {
			return true;
		}
	}
}
