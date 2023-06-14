package com.hientran.sohebox.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "bittrex_market_summary_24h_tbl", indexes = {
        @Index(name = "IDX_bittrex_market_summary", columnList = "marketName, timeStamp") })
@EntityListeners(AuditingEntityListener.class)
public class BittrexMarketSummary24hTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "marketName", columnDefinition = "varchar(15) NOT NULL")
    private String marketName;

    @Column(name = "high", precision = 16, scale = 8)
    private BigDecimal high;

    @Column(name = "low", precision = 16, scale = 8)
    private BigDecimal low;

    @Column(name = "volume", precision = 18, scale = 8)
    private BigDecimal volume;

    @Column(name = "last", precision = 16, scale = 8)
    private BigDecimal last;

    @Column(name = "baseVolume", precision = 18, scale = 8)
    private BigDecimal baseVolume;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timeStamp")
    private Date timeStamp;

    @Column(name = "bid", precision = 16, scale = 8)
    private BigDecimal bid;

    @Column(name = "ask", precision = 16, scale = 8)
    private BigDecimal ask;

    @Column(name = "openBuyOrders", columnDefinition = "int(6) NULL")
    private Integer openBuyOrders;

    @Column(name = "openSellOrders", columnDefinition = "int(6) NULL")
    private Integer openSellOrders;

    @Column(name = "prevDay", precision = 16, scale = 8)
    private BigDecimal prevDay;

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
     * Get high
     *
     * @return high
     */
    public BigDecimal getHigh() {
        return high;
    }

    /**
     * Set high
     *
     * @param high
     *            the high to set
     */
    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    /**
     * Get low
     *
     * @return low
     */
    public BigDecimal getLow() {
        return low;
    }

    /**
     * Set low
     *
     * @param low
     *            the low to set
     */
    public void setLow(BigDecimal low) {
        this.low = low;
    }

    /**
     * Get volume
     *
     * @return volume
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * Set volume
     *
     * @param volume
     *            the volume to set
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * Get last
     *
     * @return last
     */
    public BigDecimal getLast() {
        return last;
    }

    /**
     * Set last
     *
     * @param last
     *            the last to set
     */
    public void setLast(BigDecimal last) {
        this.last = last;
    }

    /**
     * Get baseVolume
     *
     * @return baseVolume
     */
    public BigDecimal getBaseVolume() {
        return baseVolume;
    }

    /**
     * Set baseVolume
     *
     * @param baseVolume
     *            the baseVolume to set
     */
    public void setBaseVolume(BigDecimal baseVolume) {
        this.baseVolume = baseVolume;
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
     * Get bid
     *
     * @return bid
     */
    public BigDecimal getBid() {
        return bid;
    }

    /**
     * Set bid
     *
     * @param bid
     *            the bid to set
     */
    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    /**
     * Get ask
     *
     * @return ask
     */
    public BigDecimal getAsk() {
        return ask;
    }

    /**
     * Set ask
     *
     * @param ask
     *            the ask to set
     */
    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    /**
     * Get openBuyOrders
     *
     * @return openBuyOrders
     */
    public Integer getOpenBuyOrders() {
        return openBuyOrders;
    }

    /**
     * Set openBuyOrders
     *
     * @param openBuyOrders
     *            the openBuyOrders to set
     */
    public void setOpenBuyOrders(Integer openBuyOrders) {
        this.openBuyOrders = openBuyOrders;
    }

    /**
     * Get openSellOrders
     *
     * @return openSellOrders
     */
    public Integer getOpenSellOrders() {
        return openSellOrders;
    }

    /**
     * Set openSellOrders
     *
     * @param openSellOrders
     *            the openSellOrders to set
     */
    public void setOpenSellOrders(Integer openSellOrders) {
        this.openSellOrders = openSellOrders;
    }

    /**
     * Get prevDay
     *
     * @return prevDay
     */
    public BigDecimal getPrevDay() {
        return prevDay;
    }

    /**
     * Set prevDay
     *
     * @param prevDay
     *            the prevDay to set
     */
    public void setPrevDay(BigDecimal prevDay) {
        this.prevDay = prevDay;
    }
}
