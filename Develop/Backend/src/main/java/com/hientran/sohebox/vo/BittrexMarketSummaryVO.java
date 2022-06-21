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
public class BittrexMarketSummaryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("MarketName")
    private String marketName;

    @JsonProperty("High")
    private BigDecimal high;

    @JsonProperty("Low")
    private BigDecimal low;

    @JsonProperty("Volume")
    private BigDecimal volume;

    @JsonProperty("Last")
    private BigDecimal last;

    @JsonProperty("BaseVolume")
    private BigDecimal baseVolume;

    @JsonProperty("TimeStamp")
    private Date timeStamp;

    @JsonProperty("Bid")
    private BigDecimal bid;

    @JsonProperty("Ask")
    private BigDecimal ask;

    @JsonProperty("OpenBuyOrders")
    private Integer openBuyOrders;

    @JsonProperty("OpenSellOrders")
    private Integer openSellOrders;

    @JsonProperty("PrevDay")
    private BigDecimal prevDay;

    @JsonProperty("Created")
    private Date created;

    /**
     * Constructor
     *
     */
    public BittrexMarketSummaryVO() {
        super();
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexMarketSummaryVO [marketName=" + marketName + ", high=" + high + ", low=" + low + ", volume="
                + volume + ", last=" + last + ", baseVolume=" + baseVolume + ", timeStamp=" + timeStamp + ", bid=" + bid
                + ", ask=" + ask + ", openBuyOrders=" + openBuyOrders + ", openSellOrders=" + openSellOrders
                + ", prevDay=" + prevDay + ", created=" + created + "]";
    }
}