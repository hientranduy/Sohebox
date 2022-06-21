package com.hientran.sohebox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "crypto_token_config_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_crypto_token_config", columnNames = { "tokenCode" }) })
public class CryptoTokenConfigTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "tokenCode", nullable = false)
    private String tokenCode;

    @Column(name = "tokenName")
    private String tokenName;

    @Column(name = "iconUrl")
    private String iconUrl;

    @Column(name = "nodeUrl")
    private String nodeUrl;

    @Column(name = "denom")
    private String denom;

    /**
     * Get tokenCode
     *
     * @return tokenCode
     */
    public String getTokenCode() {
        return tokenCode;
    }

    /**
     * Set tokenCode
     *
     * @param tokenCode
     *            the tokenCode to set
     */
    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    /**
     * Get tokenName
     *
     * @return tokenName
     */
    public String getTokenName() {
        return tokenName;
    }

    /**
     * Set tokenName
     *
     * @param tokenName
     *            the tokenName to set
     */
    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * Get iconUrl
     *
     * @return iconUrl
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Set iconUrl
     *
     * @param iconUrl
     *            the iconUrl to set
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Get nodeUrl
     *
     * @return nodeUrl
     */
    public String getNodeUrl() {
        return nodeUrl;
    }

    /**
     * Set nodeUrl
     *
     * @param nodeUrl
     *            the nodeUrl to set
     */
    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    /**
     * Get denom
     *
     * @return denom
     */
    public String getDenom() {
        return denom;
    }

    /**
     * Set denom
     *
     * @param denom
     *            the denom to set
     */
    public void setDenom(String denom) {
        this.denom = denom;
    }

    /**
     * Explanation of processing
     *
     */
    public CryptoTokenConfigTbl() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Explanation of processing
     *
     * @param tokenCode
     * @param tokenName
     * @param iconUrl
     * @param nodeUrl
     * @param denom
     */
    public CryptoTokenConfigTbl(String tokenCode, String tokenName, String iconUrl, String nodeUrl, String denom) {
        super();
        this.tokenCode = tokenCode;
        this.tokenName = tokenName;
        this.iconUrl = iconUrl;
        this.nodeUrl = nodeUrl;
        this.denom = denom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CryptoTokenConfigTbl [tokenCode=" + tokenCode + ", tokenName=" + tokenName + ", iconUrl=" + iconUrl
                + ", nodeUrl=" + nodeUrl + ", denom=" + denom + "]";
    }

}