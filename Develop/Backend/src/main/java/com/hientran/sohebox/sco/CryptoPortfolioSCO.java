package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class CryptoPortfolioSCO extends BaseSCO {

	private static final long serialVersionUID = -68140570073293062L;

	private SearchNumberVO id;

	private SearchNumberVO user;

	private SearchNumberVO token;

	public CryptoPortfolioSCO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CryptoPortfolioSCO(SearchNumberVO id, SearchNumberVO user, SearchNumberVO token) {
		super();
		this.id = id;
		this.user = user;
		this.token = token;
	}

	public SearchNumberVO getId() {
		return id;
	}

	public void setId(SearchNumberVO id) {
		this.id = id;
	}

	public SearchNumberVO getUser() {
		return user;
	}

	public void setUser(SearchNumberVO user) {
		this.user = user;
	}

	public SearchNumberVO getToken() {
		return token;
	}

	public void setToken(SearchNumberVO token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "CryptoPortfolioSCO [id=" + id + ", user=" + user + ", token=" + token + "]";
	}

}
