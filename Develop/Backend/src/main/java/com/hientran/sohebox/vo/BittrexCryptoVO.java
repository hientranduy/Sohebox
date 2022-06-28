package com.hientran.sohebox.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran12
 */
@JsonInclude(Include.NON_NULL)
public class BittrexCryptoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("CurrencyLong")
    private String currencyLong;

    @JsonProperty("MinConfirmation")
    private Integer minConfirmation;

    @JsonProperty("TxFee")
    private BigDecimal txFee;

    @JsonProperty("IsActive")
    private Boolean active;

    @JsonProperty("IsRestricted")
    private Boolean restricted;

    @JsonProperty("CoinType")
    private String coinType;

    @JsonProperty("BaseAddress")
    private String baseAddress;

    @JsonProperty("Notice")
    private String notice;

    /**
     * Constructor
     *
     */
    public BittrexCryptoVO() {
        super();
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexCryptoVO [currency=" + currency + ", currencyLong=" + currencyLong + ", minConfirmation="
                + minConfirmation + ", txFee=" + txFee + ", active=" + active + ", restricted=" + restricted
                + ", coinType=" + coinType + ", baseAddress=" + baseAddress + ", notice=" + notice + "]";
    }
}
