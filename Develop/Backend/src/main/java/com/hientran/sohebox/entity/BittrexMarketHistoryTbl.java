package com.hientran.sohebox.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author hientran
 */
@Entity
@Table(name = "bittrex_market_history_tbl", indexes = {
        @Index(name = "IDX_bittrex_market_history", columnList = "marketName, timeStamp, orderType") })
public class BittrexMarketHistoryTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timeStamp")
    private Date timeStamp;

    @Column(name = "marketName", columnDefinition = "varchar(15) NOT NULL")
    private String marketName;

    @Column(name = "quantity", precision = 16, scale = 8)
    private BigDecimal quantity;

    @Column(name = "price", precision = 16, scale = 8)
    private BigDecimal price;

    @Column(name = "total", precision = 16, scale = 8)
    private BigDecimal total;

    @Column(name = "orderType", columnDefinition = "varchar(15) NOT NULL")
    private String orderType;

    @Column(name = "countOrder", columnDefinition = " int(3) NOT NULL DEFAULT 0 COMMENT 'Total order in a bid/ask'")
    private Integer countOrder;

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
     * Get countOrder
     *
     * @return countOrder
     */
    public Integer getCountOrder() {
        return countOrder;
    }

    /**
     * Set countOrder
     *
     * @param countOrder
     *            the countOrder to set
     */
    public void setCountOrder(Integer countOrder) {
        this.countOrder = countOrder;
    }

}
