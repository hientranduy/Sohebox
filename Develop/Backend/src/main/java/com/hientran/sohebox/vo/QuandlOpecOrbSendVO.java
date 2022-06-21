package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class QuandlOpecOrbSendVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String date;

    private Double price;

    /**
     * Constructor
     *
     */
    public QuandlOpecOrbSendVO() {
        super();
    }

    /**
     * Get date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date
     *
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get price
     *
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Set price
     *
     * @param price
     *            the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "QuandlOpecOrbSendVO [date=" + date + ", price=" + price + "]";
    }

}