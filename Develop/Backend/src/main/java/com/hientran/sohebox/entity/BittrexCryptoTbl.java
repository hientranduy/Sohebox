package com.hientran.sohebox.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "bittrex_crypto_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_bittrex_crypto", columnNames = { "currency" }) }, indexes = {
                @Index(name = "IDX_bittrex_crypto", columnList = "active, restricted , coinType ") })
public class BittrexCryptoTbl extends BaseTbl {
    private static final long serialVersionUID = 1L;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "restricted")
    private Boolean restricted;

    @Column(name = "notice")
    private String notice;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "currencyLong")
    private String currencyLong;

    @Column(name = "minConfirmation")
    private Integer minConfirmation;

    @Column(name = "txFee", precision = 19, scale = 8)
    private BigDecimal txFee;

    @Column(name = "coinType")
    private String coinType;

    @Column(name = "baseAddress")
    private String baseAddress;

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
     * Get currency
     *
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Set currency
     *
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Get currencyLong
     *
     * @return currencyLong
     */
    public String getCurrencyLong() {
        return currencyLong;
    }

    /**
     * Set currencyLong
     *
     * @param currencyLong
     *            the currencyLong to set
     */
    public void setCurrencyLong(String currencyLong) {
        this.currencyLong = currencyLong;
    }

    /**
     * Get minConfirmation
     *
     * @return minConfirmation
     */
    public Integer getMinConfirmation() {
        return minConfirmation;
    }

    /**
     * Set minConfirmation
     *
     * @param minConfirmation
     *            the minConfirmation to set
     */
    public void setMinConfirmation(Integer minConfirmation) {
        this.minConfirmation = minConfirmation;
    }

    /**
     * Get txFee
     *
     * @return txFee
     */
    public BigDecimal getTxFee() {
        return txFee;
    }

    /**
     * Set txFee
     *
     * @param txFee
     *            the txFee to set
     */
    public void setTxFee(BigDecimal txFee) {
        this.txFee = txFee;
    }

    /**
     * Get coinType
     *
     * @return coinType
     */
    public String getCoinType() {
        return coinType;
    }

    /**
     * Set coinType
     *
     * @param coinType
     *            the coinType to set
     */
    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    /**
     * Get baseAddress
     *
     * @return baseAddress
     */
    public String getBaseAddress() {
        return baseAddress;
    }

    /**
     * Set baseAddress
     *
     * @param baseAddress
     *            the baseAddress to set
     */
    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

}
