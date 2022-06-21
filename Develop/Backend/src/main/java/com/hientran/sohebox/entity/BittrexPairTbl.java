package com.hientran.sohebox.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "bittrex_pair_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_bittrex_pair", columnNames = { "marketName" }) }, indexes = {
        @Index(name = "IDX_bittrex_pair", columnList = "marketCurrency, baseCurrency, active, restricted, sponsored, extractDataFlag") })
public class BittrexPairTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "marketCurrency")
    private String marketCurrency;

    @Column(name = "baseCurrency")
    private String baseCurrency;

    @Column(name = "marketCurrencyLong")
    private String marketCurrencyLong;

    @Column(name = "baseCurrencyLong")
    private String baseCurrencyLong;

    @Column(name = "actminTradeSizeive", precision = 19, scale = 8)
    private BigDecimal minTradeSize;

    @Column(name = "marketName", nullable = false)
    private String marketName;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "restricted")
    private Boolean restricted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Column(name = "notice")
    private String notice;

    @Column(name = "sponsored")
    private Boolean sponsored;

    @Column(name = "logoUrl")
    private String logoUrl;

    @Column(name = "extractDataFlag", columnDefinition = " bit(1) NOT NULL DEFAULT 0 COMMENT '1: extract data pair history'")
    private Boolean extractDataFlag;

    /**
     * Get marketCurrency
     *
     * @return marketCurrency
     */
    public String getMarketCurrency() {
        return marketCurrency;
    }

    /**
     * Set marketCurrency
     *
     * @param marketCurrency
     *            the marketCurrency to set
     */
    public void setMarketCurrency(String marketCurrency) {
        this.marketCurrency = marketCurrency;
    }

    /**
     * Get baseCurrency
     *
     * @return baseCurrency
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * Set baseCurrency
     *
     * @param baseCurrency
     *            the baseCurrency to set
     */
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     * Get marketCurrencyLong
     *
     * @return marketCurrencyLong
     */
    public String getMarketCurrencyLong() {
        return marketCurrencyLong;
    }

    /**
     * Set marketCurrencyLong
     *
     * @param marketCurrencyLong
     *            the marketCurrencyLong to set
     */
    public void setMarketCurrencyLong(String marketCurrencyLong) {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    /**
     * Get baseCurrencyLong
     *
     * @return baseCurrencyLong
     */
    public String getBaseCurrencyLong() {
        return baseCurrencyLong;
    }

    /**
     * Set baseCurrencyLong
     *
     * @param baseCurrencyLong
     *            the baseCurrencyLong to set
     */
    public void setBaseCurrencyLong(String baseCurrencyLong) {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    /**
     * Get minTradeSize
     *
     * @return minTradeSize
     */
    public BigDecimal getMinTradeSize() {
        return minTradeSize;
    }

    /**
     * Set minTradeSize
     *
     * @param minTradeSize
     *            the minTradeSize to set
     */
    public void setMinTradeSize(BigDecimal minTradeSize) {
        this.minTradeSize = minTradeSize;
    }

    /**
     * Get marketName
     *
     * @return marketName
     */
    public String getMarketName() {
        return marketName;
    }

    /**
     * Set marketName
     *
     * @param marketName
     *            the marketName to set
     */
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    /**
     * Get active
     *
     * @return active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Set active
     *
     * @param active
     *            the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Get restricted
     *
     * @return restricted
     */
    public Boolean getRestricted() {
        return restricted;
    }

    /**
     * Set restricted
     *
     * @param restricted
     *            the restricted to set
     */
    public void setRestricted(Boolean restricted) {
        this.restricted = restricted;
    }

    /**
     * Get created
     *
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Set created
     *
     * @param created
     *            the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Get notice
     *
     * @return notice
     */
    public String getNotice() {
        return notice;
    }

    /**
     * Set notice
     *
     * @param notice
     *            the notice to set
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * Get sponsored
     *
     * @return sponsored
     */
    public Boolean getSponsored() {
        return sponsored;
    }

    /**
     * Set sponsored
     *
     * @param sponsored
     *            the sponsored to set
     */
    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    /**
     * Get logoUrl
     *
     * @return logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Set logoUrl
     *
     * @param logoUrl
     *            the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * Get extractDataFlag
     *
     * @return extractDataFlag
     */
    public Boolean getExtractDataFlag() {
        return extractDataFlag;
    }

    /**
     * Set extractDataFlag
     *
     * @param extractDataFlag
     *            the extractDataFlag to set
     */
    public void setExtractDataFlag(Boolean extractDataFlag) {
        this.extractDataFlag = extractDataFlag;
    }

}
