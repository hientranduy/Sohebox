package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class VcbCurrencyVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String dateTime;

    private List<VcbCurrencyItemVO> rates;

    /**
     * Constructor
     *
     */
    public VcbCurrencyVO() {
        super();
    }

    /**
     * Get dateTime
     *
     * @return dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Set dateTime
     *
     * @param dateTime
     *            the dateTime to set
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Get rates
     *
     * @return rates
     */
    public List<VcbCurrencyItemVO> getRates() {
        return rates;
    }

    /**
     * Set rates
     *
     * @param rates
     *            the rates to set
     */
    public void setRates(List<VcbCurrencyItemVO> rates) {
        this.rates = rates;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "VcbCurrencyVO [dateTime=" + dateTime + ", rates=" + rates + "]";
    }

}