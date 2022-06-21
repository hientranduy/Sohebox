package com.hientran.sohebox.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class BittrexMarketHistoryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("TimeStamp")
    private Date timeStamp;

    @JsonProperty("Quantity")
    private BigDecimal quantity;

    @JsonProperty("Price")
    private BigDecimal price;

    @JsonProperty("Total")
    private BigDecimal total;

    @JsonProperty("FillType")
    private String fillType;

    @JsonProperty("OrderType")
    private String orderType;

    /**
     * Constructor
     *
     */
    public BittrexMarketHistoryVO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * Get price
     *
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Set price
     *
     * @param price
     *            the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Get total
     *
     * @return total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Set total
     *
     * @param total
     *            the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Get fillType
     *
     * @return fillType
     */
    public String getFillType() {
        return fillType;
    }

    /**
     * Set fillType
     *
     * @param fillType
     *            the fillType to set
     */
    public void setFillType(String fillType) {
        this.fillType = fillType;
    }

    /**
     * Get orderType
     *
     * @return orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Set orderType
     *
     * @param orderType
     *            the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * Get timeStamp
     *
     * @return timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Set timeStamp
     *
     * @param timeStamp
     *            the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexMarketHistoryVO [id=" + id + ", quantity=" + quantity + ", price=" + price + ", total=" + total
                + ", fillType=" + fillType + ", orderType=" + orderType + "]";
    }
}