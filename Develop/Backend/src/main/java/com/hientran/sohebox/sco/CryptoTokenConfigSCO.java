package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class CryptoTokenConfigSCO extends BaseSCO {

	private static final long serialVersionUID = -68140570073293062L;

	private SearchNumberVO id;

	private SearchTextVO tokenCode;

	public CryptoTokenConfigSCO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CryptoTokenConfigSCO(SearchNumberVO id, SearchTextVO tokenCode) {
		super();
		this.id = id;
		this.tokenCode = tokenCode;
	}

	public SearchNumberVO getId() {
		return id;
	}

	public void setId(SearchNumberVO id) {
		this.id = id;
	}

	public SearchTextVO getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(SearchTextVO tokenCode) {
		this.tokenCode = tokenCode;
	}

	@Override
	public String toString() {
		return "CryptoTokenConfigSCO [id=" + id + ", tokenCode=" + tokenCode + "]";
	}

}
