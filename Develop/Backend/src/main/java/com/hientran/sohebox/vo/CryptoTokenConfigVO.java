package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class CryptoTokenConfigVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String tokenCode;

	private String tokenName;

	private String iconUrl;

	private String nodeUrl;

	private String denom;

	public CryptoTokenConfigVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CryptoTokenConfigVO(Long id, String tokenCode, String tokenName, String iconUrl, String nodeUrl,
			String denom) {
		super();
		this.id = id;
		this.tokenCode = tokenCode;
		this.tokenName = tokenName;
		this.iconUrl = iconUrl;
		this.nodeUrl = nodeUrl;
		this.denom = denom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

	public String getDenom() {
		return denom;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}

	@Override
	public String toString() {
		return "CryptoTokenConfigVO [id=" + id + ", tokenCode=" + tokenCode + ", tokenName=" + tokenName + ", iconUrl="
				+ iconUrl + ", nodeUrl=" + nodeUrl + ", denom=" + denom + "]";
	}

}