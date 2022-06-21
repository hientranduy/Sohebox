package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class CryptoPortfolioVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private Long id;

	private UserVO user;

	private CryptoTokenConfigVO token;

	public CryptoPortfolioVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CryptoPortfolioVO(Long id, UserVO user, CryptoTokenConfigVO token) {
		super();
		this.id = id;
		this.user = user;
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public CryptoTokenConfigVO getToken() {
		return token;
	}

	public void setToken(CryptoTokenConfigVO token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "CryptoPortfolioVO [id=" + id + ", user=" + user + ", token=" + token + "]";
	}

}