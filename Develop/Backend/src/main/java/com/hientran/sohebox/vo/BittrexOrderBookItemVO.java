package com.hientran.sohebox.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class BittrexOrderBookItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Quantity")
    private BigDecimal quantity;

    @JsonProperty("Rate")
    private BigDecimal rate;

    /**
     * Constructor
     *
     */
    public BittrexOrderBookItemVO() {
        super();
    }

    /**
     * Get quantity
     *
     * @return quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Set quantity
     *
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * Get rate
     *
     * @return rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Set rate
     *
     * @param rate
     *            the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexOrderBookItemVO [quantity=" + quantity + ", rate=" + rate + "]";
    }
}