package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class VcbCurrencyItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String currencyCode;

    private String currencyName;

    private String buy;

    private String transfer;

    private String sell;

    /**
     * Constructor
     *
     */
    public VcbCurrencyItemVO() {
        super();
    }

    /**
     * Get currencyCode
     *
     * @return currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Set currencyCode
     *
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Get currencyName
     *
     * @return currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * Set currencyName
     *
     * @param currencyName
     *            the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * Get buy
     *
     * @return buy
     */
    public String getBuy() {
        return buy;
    }

    /**
     * Set buy
     *
     * @param buy
     *            the buy to set
     */
    public void setBuy(String buy) {
        this.buy = buy;
    }

    /**
     * Get transfer
     *
     * @return transfer
     */
    public String getTransfer() {
        return transfer;
    }

    /**
     * Set transfer
     *
     * @param transfer
     *            the transfer to set
     */
    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    /**
     * Get sell
     *
     * @return sell
     */
    public String getSell() {
        return sell;
    }

    /**
     * Set sell
     *
     * @param sell
     *            the sell to set
     */
    public void setSell(String sell) {
        this.sell = sell;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "VcbCurrencyItemVO [currencyCode=" + currencyCode + ", currencyName=" + currencyName + ", buy=" + buy
                + ", transfer=" + transfer + ", sell=" + sell + "]";
    }

}